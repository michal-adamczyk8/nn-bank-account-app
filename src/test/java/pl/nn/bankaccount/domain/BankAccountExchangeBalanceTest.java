package pl.nn.bankaccount.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import pl.nn.bankaccount.common.valueobjects.Balance;
import pl.nn.bankaccount.domain.dto.ExchangeBalanceDto;
import pl.nn.bankaccount.domain.dto.ExchangeRateDto;
import pl.nn.bankaccount.domain.dto.OpenAccountDto;

class BankAccountExchangeBalanceTest {
    private static final ExchangeRateDto EXCHANGE_RATE = new ExchangeRateDto(Currency.USD, BigDecimal.valueOf(4.09), BigDecimal.valueOf(4.17));

    @Test
    void shouldSuccessfullyBuyUsd() {
        //given
        var account = givenBankAccountWithPlnBalance(BigDecimal.valueOf(1000));

        //and
        var exchangeBalanceDto = new ExchangeBalanceDto(ExchangeOperation.BUY, Currency.USD, BigDecimal.valueOf(20));

        //when
        account.exchangeBalance(exchangeBalanceDto, EXCHANGE_RATE);

        //then
        assertUsdBalanceEqualTo(account, BigDecimal.valueOf(20));
        assertPlnBalanceEqualTo(account, BigDecimal.valueOf(1000).subtract(BigDecimal.valueOf(20).multiply(EXCHANGE_RATE.ask())));
    }

    private void assertUsdBalanceEqualTo(BankAccount bankAccount, BigDecimal expectedAmount) {
        assertThat(bankAccount.getForeignBalances()).containsEntry(Currency.USD, Balance.create(expectedAmount, Currency.USD));
    }

    private void assertPlnBalanceEqualTo(BankAccount bankAccount, BigDecimal expectedAmount) {
        assertThat(bankAccount.getPlnBalance()).isEqualTo(Balance.create(expectedAmount, Currency.PLN));
    }

    @Test
    void shouldSuccessfullySellUsd() {
        //given
        var account = givenBankAccountWithUsdBalance(BigDecimal.valueOf(20));

        //and
        var exchangeBalanceDto = new ExchangeBalanceDto(ExchangeOperation.SELL, Currency.USD, BigDecimal.valueOf(20));

        //when
        account.exchangeBalance(exchangeBalanceDto, EXCHANGE_RATE);

        //then
        assertUsdBalanceEqualTo(account, BigDecimal.ZERO);

        //and
        assertPlnBalanceEqualTo(account, BigDecimal.valueOf(1000).subtract(BigDecimal.valueOf(20).multiply(EXCHANGE_RATE.ask()))
                .add(BigDecimal.valueOf(20).multiply(EXCHANGE_RATE.bid())));
    }

    @Test
    void shouldFailWhenBuyingUsdAndInsufficientFunds() {
        //given
        var account = givenBankAccountWithPlnBalance(BigDecimal.valueOf(1000));

        //and
        var exchangeBalanceDto = new ExchangeBalanceDto(ExchangeOperation.BUY, Currency.USD, BigDecimal.valueOf(1000));

        //when
        var thrownException = catchThrowable(() -> account.exchangeBalance(exchangeBalanceDto, EXCHANGE_RATE));

        //then
        assertThat(thrownException)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Insufficient funds");

        //and
        assertThat(account.getForeignBalances()).isEmpty();
    }

    @Test
    void shouldFailWhenSellingUsdAndInsufficientFunds() {
        //given
        var account = givenBankAccountWithUsdBalance(BigDecimal.valueOf(20));

        //and
        var exchangeBalanceDto = new ExchangeBalanceDto(ExchangeOperation.SELL, Currency.USD, BigDecimal.valueOf(30));

        //when
        var thrownException = catchThrowable(() -> account.exchangeBalance(exchangeBalanceDto, EXCHANGE_RATE));

        //then
        assertThat(thrownException)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Insufficient funds");

        //and
        assertThat(account.getForeignBalances()).containsEntry(Currency.USD, Balance.create(BigDecimal.valueOf(20), Currency.USD));
    }

    @Test
    void shouldFailSellUsdWhenNoForeignBalance() {
        //given
        var account = givenBankAccountWithPlnBalance(BigDecimal.valueOf(1000));

        //and
        var exchangeBalanceDto = new ExchangeBalanceDto(ExchangeOperation.SELL, Currency.USD, BigDecimal.valueOf(20));

        //when
        var thrownException = catchThrowable(() -> account.exchangeBalance(exchangeBalanceDto, EXCHANGE_RATE));

        //then
        assertThat(thrownException)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot exchange zero foreign balance");

        //and
        assertThat(account.getForeignBalances()).isEmpty();
    }


    private BankAccount givenBankAccountWithPlnBalance(BigDecimal amount) {
        var openAccountDto = new OpenAccountDto("John", "Doe", amount);
        return BankAccount.open(openAccountDto);
    }

    private BankAccount givenBankAccountWithUsdBalance(BigDecimal amount) {
        var bankAccount = givenBankAccountWithPlnBalance(BigDecimal.valueOf(1000));
        var exchangeBalanceDto = new ExchangeBalanceDto(ExchangeOperation.BUY, Currency.USD, amount);
        bankAccount.exchangeBalance(exchangeBalanceDto, EXCHANGE_RATE);
        return bankAccount;
    }
}

