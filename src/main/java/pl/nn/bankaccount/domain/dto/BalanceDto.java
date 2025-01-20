package pl.nn.bankaccount.domain.dto;

import java.math.BigDecimal;
import pl.nn.bankaccount.domain.Currency;

public record BalanceDto(
        Currency currency,
        BigDecimal amount
) {
}
