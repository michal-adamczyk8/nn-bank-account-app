package pl.nn.bankaccount.infrastructure.api.dto;

import java.util.UUID;

public record OpenAccountResponse(
        UUID bankAccountId
) {
}
