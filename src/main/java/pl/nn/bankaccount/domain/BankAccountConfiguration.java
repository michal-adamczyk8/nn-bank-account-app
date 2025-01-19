package pl.nn.bankaccount.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BankAccountConfiguration {

    @Bean
    BankAccountFacade bankAccountFacade(final BankAccountRepository repository) {
        return new BankAccountFacade(repository);
    }
}
