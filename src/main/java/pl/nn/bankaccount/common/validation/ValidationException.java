package pl.nn.bankaccount.common.validation;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
