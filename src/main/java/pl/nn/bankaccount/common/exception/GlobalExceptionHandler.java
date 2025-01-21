package pl.nn.bankaccount.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.nn.bankaccount.common.exception.dto.ErrorMessage;
import pl.nn.bankaccount.common.validation.ValidationException;

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {
    private static final String GENERAL_ERROR_MESSAGE = "An error occurred. Pleas try again later.";

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(ValidationException e) {
        log.error("Validation error", e);
        String exceptionMessage = e.getMessage();
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), exceptionMessage);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        log.error("Internal server error", e);
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), GENERAL_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorMessage);
    }
}
