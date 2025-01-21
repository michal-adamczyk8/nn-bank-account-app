package pl.nn.bankaccount.infrastructure.currency.dto;

public class InvalidNbpResponseException extends RuntimeException {

    public InvalidNbpResponseException(String message) {
        super(message);
    }
}
