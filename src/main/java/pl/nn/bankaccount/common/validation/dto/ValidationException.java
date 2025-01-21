package pl.nn.bankaccount.common.validation.dto;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
