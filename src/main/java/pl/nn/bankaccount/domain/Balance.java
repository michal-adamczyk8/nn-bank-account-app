package pl.nn.bankaccount.domain;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static pl.nn.bankaccount.common.Validator.checkNotNull;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.nn.bankaccount.domain.dto.BalanceDto;

@Embeddable
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
class Balance {
    BigDecimal amount;
    Currency currency;

    static Balance create(BigDecimal amount, Currency currency) {
        checkNotNull(amount, "Amount cannot be null");
        checkNotNull(currency, "Currency cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        return new Balance(amount, currency);
    }

    static Balance createEmpty(Currency currency) {
        return new Balance(BigDecimal.ZERO, currency);
    }

    BalanceDto toDto() {
        return new BalanceDto(currency.name(), amount);
    }
}
