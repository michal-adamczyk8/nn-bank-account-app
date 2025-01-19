package pl.nn.bankaccount.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.nn.bankaccount.domain.dto.BankAccountDto;
import pl.nn.bankaccount.infrastructure.api.dto.OpenAccountRequest;
import pl.nn.bankaccount.infrastructure.api.dto.OpenAccountResponse;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class BankAccountIT {
    private static final String ACCOUNT_ENDPOINT = "/api/v1/account";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankAccountFacade bankAccountFacade;

    @Test
    @DisplayName("Should open account")
    void shouldOpenAccount() throws Exception {
        // given
        var openAccountRequest = givenOpenAccountRequest();

        //and
        var apiRequest = givenOpenAccountApiRequest(openAccountRequest);

        // when
        var result = mockMvc.perform(apiRequest);

        //then
        var response = result.andExpect(status().isCreated())
                .andReturn();

        //and
        var accountDetailsResponse = extractResponse(response, OpenAccountResponse.class);
        var accountDetails = bankAccountFacade.getAccountDetails(accountDetailsResponse.bankAccountId());
        assertThat(accountDetails).isNotNull();
        assertThat(accountDetails).satisfies(a -> {
            assertThat(a.firstName()).isEqualTo(openAccountRequest.firstName());
            assertThat(a.lastName()).isEqualTo(openAccountRequest.lastName());
            assertThat(a.balanceInPln().amount()).isEqualTo(openAccountRequest.initialBalanceInPln());
        });
    }


    @Test
    @DisplayName("Should get account details")
    void shouldGetAccountDetails() throws Exception {
        //given
        var accountId = accountExists();

        //and
        var apiRequest = givenGetAccountApiRequest(accountId);

        //when
        var result = mockMvc.perform(apiRequest)
                .andExpect(status().isOk());

        //then
        var response = result.andExpect(status().isOk())
                .andReturn();

        //and
        var bankAccount = extractResponse(response, BankAccountDto.class);
        assertThat(bankAccount).isNotNull();
        assertThat(bankAccount)
                .isEqualTo(bankAccountFacade.getAccountDetails(accountId));

    }

    @Test
    @DisplayName("Should return 404 when account does not exist")
    void shouldReturn404WhenAccountDoesNotExist() throws Exception {
        //given
        var accountId = UUID.randomUUID();

        //and
        var apiRequest = givenGetAccountApiRequest(accountId);

        //when
        var result = mockMvc.perform(apiRequest);

        //then
        result.andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


    private OpenAccountRequest givenOpenAccountRequest() {
        return new OpenAccountRequest("John", "Doe", BigDecimal.valueOf(1000.00));
    }

    private MockHttpServletRequestBuilder givenGetAccountApiRequest(final UUID accountId) {
        return get(ACCOUNT_ENDPOINT + "/{accountId}", accountId);
    }

    private MockHttpServletRequestBuilder givenOpenAccountApiRequest(final OpenAccountRequest request) throws Exception {
        return post(ACCOUNT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request));
    }

    private UUID accountExists() {
        OpenAccountRequest request = givenOpenAccountRequest();
        return bankAccountFacade.openAccount(request.toDto());
    }

    private <T> String asJsonString(final T request) throws Exception {
        return OBJECT_MAPPER.writeValueAsString(request);
    }

    private <T> T extractResponse(MvcResult response, Class<T> clazz) throws Exception {
        return OBJECT_MAPPER.readValue(response.getResponse().getContentAsString(), clazz);
    }
}
