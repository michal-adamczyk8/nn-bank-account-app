package pl.nn.bankaccount.domain.dto;

import java.math.BigDecimal;

public record BalanceDto(
        String currency,
        BigDecimal amount
) {
}
