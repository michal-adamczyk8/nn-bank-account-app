package pl.nn.bankaccount.domain;

import static lombok.AccessLevel.PRIVATE;
import static pl.nn.bankaccount.common.validation.Validator.checkNotNull;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.nn.bankaccount.domain.dto.BankAccountDto;
import pl.nn.bankaccount.domain.dto.BankAccountNotFoundException;
import pl.nn.bankaccount.domain.dto.ExchangeBalanceDto;
import pl.nn.bankaccount.domain.dto.ExchangeRateDto;
import pl.nn.bankaccount.domain.dto.OpenAccountDto;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class BankAccountFacade {

    BankAccountRepository repository;
    CurrencyExchange currencyExchange;

    public UUID openAccount(OpenAccountDto dto) {
        checkNotNull(dto, "OpenAccountDto required");
        BankAccount account = BankAccount.open(dto);
        repository.save(account);
        return account.getId();
    }

    public BankAccountDto getAccountDetails(UUID accountId) {
        checkNotNull(accountId, "accountId required");
        BankAccount account = repository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException(accountId));
        return account.toDto();
    }

    public void exchangeBalance(UUID accountId, ExchangeBalanceDto dto) {
        checkNotNull(dto, "ExchangeBalanceDto required");
        checkNotNull(accountId, "accountId required");
        ExchangeRateDto exchangeRate = currencyExchange.getExchangeRate(dto.currency());
        BankAccount account = repository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException(accountId));
        account.exchangeBalance(dto, exchangeRate);
        repository.save(account);
    }
}
