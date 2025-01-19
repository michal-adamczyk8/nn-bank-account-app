package pl.nn.bankaccount.infrastructure.api.dto;

import java.math.BigDecimal;
import pl.nn.bankaccount.domain.dto.OpenAccountDto;

public record OpenAccountRequest(
        String firstName,
        String lastName,
        BigDecimal initialBalanceInPln
) {
    public OpenAccountDto toDto() {
        return new OpenAccountDto(firstName, lastName, initialBalanceInPln);
    }
}
