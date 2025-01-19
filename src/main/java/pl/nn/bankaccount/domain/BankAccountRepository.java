package pl.nn.bankaccount.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}
