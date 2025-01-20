package pl.nn.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NnBankAccountAppApplication {
	//TODO: Exception handling
	//TODO: Logging
	//TODO: Persistence after restart
	//TODO: Hateos links
	//TODO: Integration with nbp
	//TODO: Dockerize
	//TODO: exchange feature
	//TODO: Tests
	//TODO: Swagger
	//TODO: cache
	//TODO: security
	//TODO: spring profiles
	//TODO: make integration tests run while building

	public static void main(String[] args) {
		SpringApplication.run(NnBankAccountAppApplication.class, args);
	}

}
