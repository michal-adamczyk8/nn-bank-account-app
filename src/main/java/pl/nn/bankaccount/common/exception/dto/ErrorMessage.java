package pl.nn.bankaccount.common.exception.dto;

public record ErrorMessage(
        int errorCode,
        String message
) {
}
