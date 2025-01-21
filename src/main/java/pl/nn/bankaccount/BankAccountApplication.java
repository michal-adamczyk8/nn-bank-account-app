package pl.nn.bankaccount;

import java.time.ZoneOffset;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BankAccountApplication {
	public static final ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.UTC;

	//TODO: Exception handling
	//TODO: Logging
	//TODO: Hateos links
	//TODO: Dockerize
	//TODO: Tests
	//TODO: Swagger
	//TODO: cache
	//TODO: security
	//TODO: spring profiles
	//TODO: make integration tests run while building
	//TODO: tests for npb integartion
	//even sourcing?

	public static void main(String[] args) {
		SpringApplication.run(BankAccountApplication.class, args);
	}

}
