package pl.nn.bankaccount.infrastructure.currency;

import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientConfiguration {

    @Bean
    NbpCurrencyExchange nbpCurrencyExchange(NbpFeignClient nbpFeignClient) {
        return new NbpCurrencyExchange(nbpFeignClient);
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    ErrorDecoder nbpErrorDecoder() {
        return new NbpErrorDecoder();
    }
}
