package pl.nn.bankaccount.domain;

import static java.util.Objects.requireNonNull;
import static pl.nn.bankaccount.common.validation.Validator.checkNotBlank;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.nn.bankaccount.common.persistence.BaseEntity;
import pl.nn.bankaccount.domain.dto.BalanceDto;
import pl.nn.bankaccount.domain.dto.BankAccountDto;
import pl.nn.bankaccount.domain.dto.ExchangeBalanceDto;
import pl.nn.bankaccount.domain.dto.ExchangeRateDto;
import pl.nn.bankaccount.domain.dto.OpenAccountDto;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PACKAGE)
class BankAccount extends BaseEntity {

    private String firstName;
    private String lastName;

    @Embedded
    private Balance plnBalance;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Currency, Balance> foreignBalances;

    BankAccount(String firstName, String lastName, Balance initialBalanceInPln, Map<Currency, Balance> foreignBalances) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.plnBalance = initialBalanceInPln;
        this.foreignBalances = foreignBalances;
    }

    static BankAccount open(OpenAccountDto dto) {
        String firstName = dto.firstName();
        String lastName = dto.lastName();
        checkNotBlank(firstName, "First name required");
        checkNotBlank(lastName, "Last name required");
        Balance initialBalanceInPln = new Balance(dto.initialBalanceInPln(), Currency.PLN);
        Map<Currency, Balance> foreignBalances = new EnumMap<>(Currency.class);
        return new BankAccount(firstName, lastName, initialBalanceInPln, foreignBalances);
    }

    public void exchangeBalance(ExchangeBalanceDto dto, ExchangeRateDto exchangeRate) {
        //TODO: null validation?
        if (requireNonNull(dto.operation()) == ExchangeOperation.BUY) {
            buyCurrency(dto, exchangeRate);
        } else {
            sellCurrency(dto, exchangeRate);
        }
    }

    private void buyCurrency(ExchangeBalanceDto dto, ExchangeRateDto exchangeRate) {
        Currency currencyToBuy = dto.currency();
        BigDecimal amountToBuy = dto.amount();
        BigDecimal askPrice = exchangeRate.ask();
        BigDecimal exchangedAmount = amountToBuy.multiply(askPrice);
        if (plnBalance.hasInsufficientFunds(exchangedAmount)) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        foreignBalances.putIfAbsent(currencyToBuy, new Balance(BigDecimal.ZERO, currencyToBuy));
        Balance foreignBalance = foreignBalances.get(currencyToBuy);

        plnBalance = plnBalance.subtract(exchangedAmount);
        Balance updatedForeignBalance = foreignBalance.add(amountToBuy);
        foreignBalances.put(currencyToBuy, updatedForeignBalance);
    }

    private void sellCurrency(ExchangeBalanceDto dto, ExchangeRateDto exchangeRate) {
        Currency currencyToSell = dto.currency();
        BigDecimal amountToSell = dto.amount();
        if (foreignBalances.isEmpty()) {
            throw new IllegalArgumentException("Cannot exchange zero foreign balance");
        }
        if (!foreignBalances.containsKey(currencyToSell)) {
            throw new IllegalArgumentException("No balance in " + currencyToSell + " currency");
        }
        Balance foreignBalance = foreignBalances.get(currencyToSell);
        if (foreignBalance.hasInsufficientFunds(amountToSell)) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        BigDecimal bidPrice = exchangeRate.bid();
        BigDecimal exchangedAmount = amountToSell.multiply(bidPrice);
        foreignBalances.put(currencyToSell, foreignBalance.subtract(amountToSell));
        plnBalance = plnBalance.add(exchangedAmount);
    }


    BankAccountDto toDto() {
        Set<BalanceDto> foreignBalancesDto = foreignBalances.isEmpty() ? Set.of() :
                foreignBalances.values().stream()
                        .map(Balance::toDto)
                        .collect(Collectors.toSet());
        return new BankAccountDto(getId(), firstName, lastName, plnBalance.toDto(), foreignBalancesDto);
    }
}
