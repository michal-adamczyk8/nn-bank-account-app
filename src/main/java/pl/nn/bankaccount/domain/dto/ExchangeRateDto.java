package pl.nn.bankaccount.domain.dto;

import java.math.BigDecimal;
import pl.nn.bankaccount.domain.Currency;

public record ExchangeRateDto(
        Currency currency,
        BigDecimal bid,
        BigDecimal ask
) {
}
