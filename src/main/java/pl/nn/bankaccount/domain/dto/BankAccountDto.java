package pl.nn.bankaccount.domain.dto;

import java.util.Set;
import java.util.UUID;

public record BankAccountDto(
        UUID id,
        String firstName,
        String lastName,
        BalanceDto balanceInPln,
        Set<BalanceDto> foreignBalances
) {
}
