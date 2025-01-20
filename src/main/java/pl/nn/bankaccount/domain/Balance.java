package pl.nn.bankaccount.domain;

import static lombok.AccessLevel.PROTECTED;
import static pl.nn.bankaccount.common.validation.Validator.checkNotNull;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.NoArgsConstructor;
import pl.nn.bankaccount.domain.dto.BalanceDto;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
class Balance {
    BigDecimal amount;
    Currency currency;

    Balance(BigDecimal amount, Currency currency) {
        checkNotNull(amount, "Amount required");
        checkNotNull(currency, "Currency required");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.amount = amount;
        this.currency = currency;
    }

    Balance add(BigDecimal amount) {
        BigDecimal updatedAmount = this.amount.add(amount);
        return new Balance(updatedAmount, currency);
    }

    Balance subtract(BigDecimal amount) {
        if (this.amount.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        BigDecimal updatedAmount = this.amount.subtract(amount);
        return new Balance(updatedAmount, currency);
    }

    boolean hasInsufficientFunds(BigDecimal amount) {
        return this.amount.compareTo(amount) < 0;
    }

    BalanceDto toDto() {
        return new BalanceDto(currency, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Balance balance = (Balance) o;
        return amount.compareTo(balance.amount) == 0 && currency == balance.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
