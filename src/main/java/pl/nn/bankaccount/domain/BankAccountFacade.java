package pl.nn.bankaccount.domain;

import static lombok.AccessLevel.PRIVATE;
import static pl.nn.bankaccount.common.validation.Validator.checkNotNull;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.nn.bankaccount.domain.dto.BankAccountDto;
import pl.nn.bankaccount.domain.dto.BankAccountNotFoundException;
import pl.nn.bankaccount.domain.dto.ExchangeBalanceDto;
import pl.nn.bankaccount.domain.dto.ExchangeRateDto;
import pl.nn.bankaccount.domain.dto.OpenAccountDto;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class BankAccountFacade {

    BankAccountRepository repository;
    CurrencyExchange currencyExchange;

    public UUID openAccount(OpenAccountDto dto) {
        checkNotNull(dto, "OpenAccountDto required");
        BankAccount account = BankAccount.open(dto);
        repository.save(account);
        log.info("Opened account with id: {}", account.getId());
        return account.getId();
    }

    public BankAccountDto getAccountDetails(UUID accountId) {
        checkNotNull(accountId, "accountId required");
        BankAccount account = repository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException(accountId));
        return account.toDto();
    }

    public void exchangeBalance(UUID accountId, ExchangeBalanceDto dto) {
        checkNotNull(dto, "ExchangeBalanceDto required");
        checkNotNull(accountId, "accountId required");
        ExchangeRateDto exchangeRate = currencyExchange.getExchangeRate(dto.currency());
        log.info("Got exchange rate: {}", exchangeRate);
        BankAccount account = repository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException(accountId));
        account.exchangeBalance(dto, exchangeRate);
        repository.save(account);
        log.info("Exchanged balance for account with id: {}", accountId);
    }
}
