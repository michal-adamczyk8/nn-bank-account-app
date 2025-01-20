package pl.nn.bankaccount.domain;

import static lombok.AccessLevel.PRIVATE;
import static pl.nn.bankaccount.common.Validator.checkNotNull;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pl.nn.bankaccount.domain.dto.BankAccountDto;
import pl.nn.bankaccount.domain.dto.BankAccountNotFoundException;
import pl.nn.bankaccount.domain.dto.OpenAccountDto;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class BankAccountFacade {

    BankAccountRepository repository;
    CurrencyExchange currencyExchange;

    public UUID openAccount(OpenAccountDto dto) {
        checkNotNull(dto, "OpenAccountDto cannot be null");
        BankAccount account = BankAccount.open(dto);
        repository.save(account);
        return account.getId();
    }

    public BankAccountDto getAccountDetails(UUID accountId) {
        checkNotNull(accountId, "accountId cannot be null");
        BankAccount account = repository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException(accountId));
        return account.toDto();
    }
}
