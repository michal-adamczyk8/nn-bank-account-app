package pl.nn.bankaccount.domain.dto;

import pl.nn.bankaccount.domain.Currency;

public record ExchangeRateDto(
        Currency currency,
        double bid,
        double ask
) {
}
