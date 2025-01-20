package pl.nn.bankaccount.infrastructure.currency.dto;

public record RateResponse(
        String no,
        String effectiveDate,
        double bid,
        double ask
) {
}
