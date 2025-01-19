package pl.nn.bankaccount.domain;

import static pl.nn.bankaccount.common.Validator.checkNotBlank;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.nn.bankaccount.domain.dto.BankAccountDto;
import pl.nn.bankaccount.domain.dto.OpenAccountDto;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class BankAccount {

    @Id
    @Getter
    private UUID id;
    private String firstName;
    private String lastName;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "pln__balance_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "pln_balance_currency"))
    private Balance plnBalance;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "usd__balance_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "usd_balance_currency"))
    private Balance usdBalance;

    static BankAccount open(OpenAccountDto dto) {
        UUID id = UUID.randomUUID();
        String firstName = dto.firstName();
        String lastName = dto.lastName();
        checkNotBlank(firstName, "First name required");
        checkNotBlank(lastName, "Last name required");
        Balance initialBalanceInPln = Balance.create(dto.initialBalanceInPln(), Currency.PLN);
        Balance initialBalanceInUsd = Balance.createEmpty(Currency.USD);
        return new BankAccount(id, firstName, lastName, initialBalanceInPln, initialBalanceInUsd);
    }


    BankAccountDto toDto() {
        return new BankAccountDto(id, firstName, lastName, plnBalance.toDto(), usdBalance.toDto());
    }
}
