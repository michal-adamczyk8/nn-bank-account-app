package pl.nn.bankaccount.infrastructure.currency;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.nn.bankaccount.infrastructure.currency.dto.CurrencyExchangeResponse;

@FeignClient(
        name = "nbp",
        url = "${nbp.url}")
interface NbpFeignClient {
    @GetMapping("/exchangerates/rates/c/{currency}/?format=json")
    CurrencyExchangeResponse getCurrencyExchangeRate(@PathVariable String currency);
}
