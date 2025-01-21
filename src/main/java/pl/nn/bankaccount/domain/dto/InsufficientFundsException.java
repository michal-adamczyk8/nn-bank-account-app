package pl.nn.bankaccount.domain.dto;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(UUID accountId) {
        super("Insufficient funds on account: " + accountId);
    }
}
