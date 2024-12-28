package edu.ups.minibank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ups.minibank.controller.dto.TransferRequestDTO;
import edu.ups.minibank.exceptions.InsufficientFundsException;
import edu.ups.minibank.service.BankAccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountServiceMock;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("BAC_CA1: Given enough funds, when call /transfer endpoint then it should return status code 200")
    void whenCallTransferEndpointWithHighFundsItShouldReturn200() throws Exception{

        //@Given
        int originAccountId = 1;
        int destinyAccountId = 2;
        BigDecimal amount = BigDecimal.valueOf(100);
        TransferRequestDTO dto = new TransferRequestDTO(destinyAccountId, amount);

        doNothing().when(bankAccountServiceMock).transferBetweenBankAccounts(anyInt(), anyInt(), any(BigDecimal.class));


        //@When
        mockMvc.perform(
                    post(String.format("/api/accounts/%d/transfer", originAccountId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(("BAC_CA2: Given low funds, when call /transfer endpoint then it should return status code 400"))
    void whenCallTransferEndpointWithLowFundsItShouldReturn400() throws Exception{
        //@Given
        int originAccountId = 1;
        int destinyAccountId = 2;
        TransferRequestDTO dto = new TransferRequestDTO(destinyAccountId, BigDecimal.valueOf(100));


        doThrow(InsufficientFundsException.class)
                .when(bankAccountServiceMock)
                .transferBetweenBankAccounts(
                        anyInt(),
                        anyInt(),
                        any(BigDecimal.class));
        //@When
        mockMvc.perform(
                        post(String.format("/api/accounts/%d/transfer", originAccountId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest());
    }
}
