package pl.nn.bankaccount.domain.dto;

import java.util.UUID;

public record BankAccountDto(
        UUID id,
        String firstName,
        String lastName,
        BalanceDto balanceInPln,
        BalanceDto balanceInUsd
){
}
