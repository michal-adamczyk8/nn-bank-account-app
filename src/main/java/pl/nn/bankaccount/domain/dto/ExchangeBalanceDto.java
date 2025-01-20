package pl.nn.bankaccount.domain.dto;

import java.math.BigDecimal;
import pl.nn.bankaccount.domain.Currency;
import pl.nn.bankaccount.domain.ExchangeOperation;

public record ExchangeBalanceDto(
        ExchangeOperation operation,
        Currency currency,
        BigDecimal amount
) {
}
