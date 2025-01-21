package pl.nn.bankaccount;

import java.time.ZoneOffset;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BankAccountApplication {
	public static final ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.UTC;
	//TODO: spring profiles
	//TODO: tests for npb integartion

	public static void main(String[] args) {
		SpringApplication.run(BankAccountApplication.class, args);
	}

}
