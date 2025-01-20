package pl.nn.bankaccount.infrastructure.currency.dto;

import java.util.List;
import pl.nn.bankaccount.domain.Currency;
import pl.nn.bankaccount.domain.dto.ExchangeRateDto;

public record CurrencyExchangeResponse(
        String table,
        String currency,
        String code,
        List<RateResponse> rates
) {

    public ExchangeRateDto toExchangeRateDto(Currency currency) {
        RateResponse rate = rates.getFirst();
        return new ExchangeRateDto(currency, rate.bid(), rate.ask());
    }
}
