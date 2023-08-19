package com.sample.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.bank.model.*;
import com.sample.bank.model.dto.AccountDTO;
import com.sample.bank.model.enums.TransferResult;
import com.sample.bank.service.account.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void testCreateAccount() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        AccountRequest request = AccountRequestTest.getAccountRequest();
        AccountDTO createdAccount = AccountTest.getAccountDTO();


        when(accountService.createAccount(1L, BigDecimal.valueOf(1000.0))).thenReturn(createdAccount);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAccount_ValidId() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        AccountDTO account = AccountTest.getAccountDTO();

        when(accountService.getAccount(1L)).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/1"))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetBalance_ValidId() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        when(accountService.getBalance(1L)).thenReturn(BigDecimal.valueOf(1000.0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/1/balance"))
                .andExpect(status().isOk());
    }


    @Test
    public void testTransferAmount_Success() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        TransferRequest request = TransferRequestTest.getTransferRequest();
        when(accountService.transferAmount(1L, 2L, BigDecimal.valueOf(100.0))).thenReturn(TransferResult.SUCCESS);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testTransferAmount_InsufficientBalance() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        TransferRequest request = TransferRequestTest.getTransferRequest();

        when(accountService.transferAmount(1L, 2L, BigDecimal.valueOf(100.0))).thenReturn(TransferResult.INSUFFICIENT_BALANCE);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void testTransferAmount_Failed() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        TransferRequest request = TransferRequestTest.getTransferRequest();
        request.setToAccountId(1L);
        request.setFromAccountId(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
