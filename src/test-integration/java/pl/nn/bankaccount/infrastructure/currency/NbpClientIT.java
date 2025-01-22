package pl.nn.bankaccount.infrastructure.currency;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import pl.nn.bankaccount.domain.Currency;
import pl.nn.bankaccount.domain.dto.ExchangeRateDto;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeException;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeRateNotFoundException;
import pl.nn.bankaccount.infrastructure.currency.dto.InvalidNbpResponseException;

@SpringBootTest
@ActiveProfiles("test")
@EnableWireMock({
        @ConfigureWireMock(
                name = "nbp-api",
                port = 65000,
                baseUrlProperties = "nbp.api.url",
                filesUnderDirectory = "src/test-integration/resources"
        )
})
class NbpClientIT {

    private static final String NBP_API = "/exchangerates/rates/c/%s/?format=json";

    @Autowired
    private NbpCurrencyExchange nbpCurrencyExchange;

    @Test
    @DisplayName("Should return correct exchange rate")
    void shouldReturnCorrectExchangeRate() {
        //given
        Currency currency = Currency.USD;

        //and
        stubFor(get(format(NBP_API, currency))
                .willReturn(successfulNbpApiResponse()));

        //when
        ExchangeRateDto exchangeRate = nbpCurrencyExchange.getExchangeRate(currency);

        //then
        assertThat(exchangeRate).isNotNull();
        assertThat(exchangeRate.ask()).isEqualByComparingTo(BigDecimal.valueOf(4.1740));
        assertThat(exchangeRate.bid()).isEqualByComparingTo(BigDecimal.valueOf(4.0914));
        assertThat(exchangeRate.currency()).isEqualTo(currency);
    }

    @Test
    @DisplayName("Should throw an exception when nbp response 404")
    void shouldThrowAnExceptionWhenNppResponse404() {
        //given
        Currency currency = Currency.USD;

        //and
        stubFor(get(format(NBP_API, currency))
                .willReturn(notFoundNbpApiResponse()));

        //when
        Throwable throwable = catchThrowable(() -> nbpCurrencyExchange.getExchangeRate(currency));

        //then
        assertThat(throwable).isInstanceOf(CurrencyExchangeRateNotFoundException.class);
    }

    @Test
    @DisplayName("Should throw an exception when nbp api response 400")
    void shouldThrowExceptionWhenNbpResponse400() {
        //given
        Currency currency = Currency.USD;

        //and
        stubFor(get(format(NBP_API, currency))
                .willReturn(badRequestNbpApiResponse()));

        //when
        Throwable throwable = catchThrowable(() -> nbpCurrencyExchange.getExchangeRate(currency));

        //then
        assertThat(throwable).isInstanceOf(CurrencyExchangeException.class);
    }

    @Test
    @DisplayName("Should throw an exception when missing ask price in response from nbp")
    void shouldThrowAnExceptionWhenMissingAskPriceInResponse() {
        //given
        Currency currency = Currency.USD;

        //and
        stubFor(get(format(NBP_API, currency))
                .willReturn(missingAskPriceApiResponse()));

        //when
        Throwable throwable = catchThrowable(() -> nbpCurrencyExchange.getExchangeRate(currency));

        //then
        assertThat(throwable).isInstanceOf(InvalidNbpResponseException.class);
    }

    @Test
    @DisplayName("Should throw an exception when missing bid price in response from nbp")
    void shouldThrowAnExceptionWhenMissingBidPriceInResponse() {
        //given
        Currency currency = Currency.USD;

        //and
        stubFor(get(format(NBP_API, currency))
                .willReturn(missingBidPriceApiResponse()));

        //when
        Throwable throwable = catchThrowable(() -> nbpCurrencyExchange.getExchangeRate(currency));

        //then
        assertThat(throwable).isInstanceOf(InvalidNbpResponseException.class);
    }


    private static ResponseDefinitionBuilder successfulNbpApiResponse() {
        return aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBodyFile("usd_successful_exchange_response.json");
    }

    private static ResponseDefinitionBuilder notFoundNbpApiResponse() {
        return aResponse()
                .withStatus(404)
                .withHeader("Content-Type", "application/json")
                .withBody("404 NotFound");
    }

    private static ResponseDefinitionBuilder badRequestNbpApiResponse() {
        return aResponse()
                .withStatus(400)
                .withHeader("Content-Type", "application/json")
                .withBody("400 Bad Request");
    }

    private static ResponseDefinitionBuilder missingAskPriceApiResponse() {
        return aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBodyFile("usd_missing_ask_exchange_response.json");
    }

    private static ResponseDefinitionBuilder missingBidPriceApiResponse() {
        return aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBodyFile("usd_missing_bid_exchange_response.json");
    }
}
