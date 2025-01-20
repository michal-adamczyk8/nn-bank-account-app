package pl.nn.bankaccount.domain;

import pl.nn.bankaccount.domain.dto.ExchangeRateDto;

public interface CurrencyExchange {
    ExchangeRateDto getExchangeRate(Currency currency);
}
