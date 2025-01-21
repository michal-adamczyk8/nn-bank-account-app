package pl.nn.bankaccount.infrastructure.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.nn.bankaccount.common.exception.dto.ErrorMessage;
import pl.nn.bankaccount.domain.dto.BankAccountNotFoundException;
import pl.nn.bankaccount.domain.dto.InsufficientFundsException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@ControllerAdvice
public class BankAccountControllerAdvice {

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleBankAccountNotFound(BankAccountNotFoundException e) {
        log.error("Bank account not found", e);
        String exceptionMessage = e.getMessage();
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), exceptionMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorMessage> handleInsufficientFunds(InsufficientFundsException e) {
        log.error("Insufficient funds", e);
        String exceptionMessage = e.getMessage();
        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), exceptionMessage);
        return ResponseEntity.badRequest().body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), exceptionMessage));
    }
}
