package pl.nn.bankaccount.infrastructure.api.dto;

import java.math.BigDecimal;
import pl.nn.bankaccount.domain.Currency;
import pl.nn.bankaccount.domain.ExchangeOperation;
import pl.nn.bankaccount.domain.dto.ExchangeBalanceDto;

public record ExchangeBalanceRequest(
        ExchangeOperation operation,
        Currency currency,
        BigDecimal amount
) {

    public ExchangeBalanceDto toDto() {
        return new ExchangeBalanceDto(operation, currency, amount);
    }
}
