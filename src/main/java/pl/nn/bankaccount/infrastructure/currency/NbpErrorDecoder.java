package pl.nn.bankaccount.infrastructure.currency;

import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStream;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeException;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeRateNotFoundException;

class NbpErrorDecoder implements ErrorDecoder {
    private static final String LIMIT_EXCEEDED_MESSAGE = "Przekroczony limit";

    @Override
    public Exception decode(final String s, final Response response) {
        String exceptionMessage;
        try (InputStream bodyIs = response.body().asInputStream()) {
            exceptionMessage = new String(bodyIs.readAllBytes());
        } catch (IOException e) {
            throw new CurrencyExchangeException("Unexpected error during currency exchange rate retrieval");
        }
        return switch (response.status()) {
            case 404 -> new CurrencyExchangeRateNotFoundException("Currency exchange rate not found");
            case 400 -> exceptionMessage.contains(LIMIT_EXCEEDED_MESSAGE) ?
                    new CurrencyExchangeException("Currency exchange rate limit exceeded") :
                    new CurrencyExchangeException("Error during currency exchange rate retrieval");
            default -> new CurrencyExchangeException("Unexpected error during currency exchange rate retrieval");
        };
    }
}
