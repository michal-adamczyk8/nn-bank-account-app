package pl.nn.bankaccount.domain.dto;

import java.util.UUID;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(UUID accountId) {
        super("Bank account with id " + accountId + " not found");
    }
}
