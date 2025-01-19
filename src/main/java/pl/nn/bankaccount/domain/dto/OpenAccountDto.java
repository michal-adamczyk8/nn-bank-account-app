package pl.nn.bankaccount.domain.dto;

import java.math.BigDecimal;

public record OpenAccountDto(
        String firstName,
        String lastName,
        BigDecimal initialBalanceInPln
) {
}
