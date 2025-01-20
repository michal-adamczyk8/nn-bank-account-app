package pl.nn.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BankAccountApplication {
	//TODO: Exception handling
	//TODO: Logging
	//TODO: Persistence after restart
	//TODO: Hateos links
	//TODO: Dockerize
	//TODO: Tests
	//TODO: Swagger
	//TODO: cache
	//TODO: security
	//TODO: spring profiles
	//TODO: make integration tests run while building
	//TODO: add optimistic locking
	//TODO: add base entity
	//TODO: tests for npb integartion

	public static void main(String[] args) {
		SpringApplication.run(BankAccountApplication.class, args);
	}

}
