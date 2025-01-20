package pl.nn.bankaccount.infrastructure.api;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nn.bankaccount.domain.BankAccountFacade;
import pl.nn.bankaccount.domain.dto.BankAccountDto;
import pl.nn.bankaccount.infrastructure.api.dto.ExchangeBalanceRequest;
import pl.nn.bankaccount.infrastructure.api.dto.OpenAccountRequest;
import pl.nn.bankaccount.infrastructure.api.dto.OpenAccountResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class BankAccountController {

    BankAccountFacade bankAccountFacade;

    @PostMapping
    ResponseEntity<OpenAccountResponse> openAccount(@RequestBody OpenAccountRequest request) {
        UUID bankAccountId = bankAccountFacade.openAccount(request.toDto());
        OpenAccountResponse response = new OpenAccountResponse(bankAccountId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    ResponseEntity<BankAccountDto> getAccountDetails(@PathVariable UUID accountId) {
        BankAccountDto accountDetails = bankAccountFacade.getAccountDetails(accountId);
        return ResponseEntity.ok(accountDetails);
    }

    @PatchMapping("/{accountId}")
    ResponseEntity<BankAccountDto> exchangeBalance(@PathVariable UUID accountId, @RequestBody ExchangeBalanceRequest request) {
        bankAccountFacade.exchangeBalance(accountId, request.toDto());
        return ResponseEntity.noContent().build();
    }
}
