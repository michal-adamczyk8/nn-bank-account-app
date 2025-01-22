package pl.nn.bankaccount.infrastructure.currency;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.nn.bankaccount.common.exception.dto.ErrorMessage;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeException;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeRateNotFoundException;
import pl.nn.bankaccount.infrastructure.currency.dto.InvalidNbpResponseException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class NbpControllerAdvice {

    @ExceptionHandler(CurrencyExchangeException.class)
    ResponseEntity<ErrorMessage> handleCurrencyExchangeException(CurrencyExchangeException e) {
        ErrorMessage errorMessage = new ErrorMessage(500, e.getMessage());
        return ResponseEntity.status(500).body(errorMessage);
    }

    @ExceptionHandler({CurrencyExchangeRateNotFoundException.class, InvalidNbpResponseException.class})
    ResponseEntity<ErrorMessage> handleCurrencyExchangeRateNotFoundException(RuntimeException e) {
        ErrorMessage errorMessage = new ErrorMessage(400, e.getMessage());
        return ResponseEntity.status(400).body(errorMessage);
    }

}
