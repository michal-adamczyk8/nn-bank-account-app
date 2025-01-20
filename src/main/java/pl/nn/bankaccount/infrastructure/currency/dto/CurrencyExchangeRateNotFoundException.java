package pl.nn.bankaccount.infrastructure.currency.dto;

public class CurrencyExchangeRateNotFoundException extends RuntimeException {

    public CurrencyExchangeRateNotFoundException(String message) {
        super(message);
    }
}
