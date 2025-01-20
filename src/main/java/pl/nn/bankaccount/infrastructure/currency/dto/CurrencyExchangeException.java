package pl.nn.bankaccount.infrastructure.currency.dto;

public class CurrencyExchangeException extends RuntimeException {

    public CurrencyExchangeException(String message) {
        super(message);
    }
}
