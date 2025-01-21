package pl.nn.bankaccount.infrastructure.currency;

import static lombok.AccessLevel.PRIVATE;
import static pl.nn.bankaccount.common.validation.Validator.checkNotNull;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.nn.bankaccount.domain.Currency;
import pl.nn.bankaccount.domain.CurrencyExchange;
import pl.nn.bankaccount.domain.dto.ExchangeRateDto;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeResponse;
import pl.nn.bankaccount.infrastructure.currency.dto.InvalidNbpResponseException;
import pl.nn.bankaccount.infrastructure.currency.dto.RateResponse;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
class NbpCurrencyExchange implements CurrencyExchange {

    NbpFeignClient client;

    @Override
    public ExchangeRateDto getExchangeRate(Currency currency) {
        log.info("Getting exchange rate for currency: {}", currency);
        checkNotNull(currency, "Currency required");
        CurrencyExchangeResponse response = client.getCurrencyExchangeRate(currency.name());
        validateResponse(response);
        return response.toExchangeRateDto(currency);
    }

    private void validateResponse(CurrencyExchangeResponse response) {
        if (response.rates().isEmpty()) {
            throw new InvalidNbpResponseException("No exchange rates found");
        }

        RateResponse rate = response.rates().getFirst();
        if (Objects.isNull(rate.bid())) {
            throw new InvalidNbpResponseException("No bid rate found");
        }
        if (Objects.isNull(rate.ask())) {
            throw new InvalidNbpResponseException("No ask rate found");
        }

    }
}
