package pl.nn.bankaccount.infrastructure.currency;

import static lombok.AccessLevel.PRIVATE;
import static pl.nn.bankaccount.common.Validator.checkNotNull;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pl.nn.bankaccount.domain.Currency;
import pl.nn.bankaccount.domain.CurrencyExchange;
import pl.nn.bankaccount.domain.dto.ExchangeRateDto;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeResponse;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
class NbpCurrencyExchange implements CurrencyExchange {

    NbpFeignClient client;

    @Override
    public ExchangeRateDto getExchangeRate(Currency currency) {
        checkNotNull(currency, "Currency required");
        CurrencyExchangeResponse response = client.getCurrencyExchangeRate(currency.name());
        return response.toExchangeRateDto(currency);
    }
}
