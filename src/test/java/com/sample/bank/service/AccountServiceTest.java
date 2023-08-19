package com.sample.bank.service;

import com.sample.bank.exception.AccountNotFoundException;
import com.sample.bank.model.AccountTest;
import com.sample.bank.model.CustomerTest;
import com.sample.bank.model.dto.AccountDTO;
import com.sample.bank.model.entity.Account;
import com.sample.bank.model.entity.Customer;
import com.sample.bank.model.entity.TransferHistory;
import com.sample.bank.model.enums.TransferResult;
import com.sample.bank.repository.AccountRepository;
import com.sample.bank.repository.CustomerRepository;
import com.sample.bank.repository.TransferRepository;
import com.sample.bank.service.account.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransferRepository transferRepository;
    @Mock
    private CustomerRepository customerRepository;


    @Test
    public void createAccount_Test(){
        Account account = AccountTest.getAccount();

        when(customerRepository.findById(account.getCustomer().getId())).thenReturn(Optional.of(CustomerTest.getCustomer()));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO accountDTO = accountService.createAccount(1L, BigDecimal.valueOf(100.0));

        assertNotNull(accountDTO);
        assertEquals(account.getId(),accountDTO.getId());
        assertEquals(account.getBalance(),accountDTO.getBalance());
    }

    @Test
    void getAccount_test() {
        Long accountId = 1L;
        Account account = AccountTest.getAccount();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountDTO accountDTO = accountService.getAccount(accountId);

        assertNotNull(accountDTO);
        assertEquals(account.getId(), accountDTO.getId());
    }

    @Test
    void testGetAccountNotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(accountId));
    }
    @Test
    void getBalance_Test() {
        Long accountId = 1L;
        BigDecimal balance = new BigDecimal("1000.00");

        Account account = AccountTest.getAccountWithBigAmount();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        BigDecimal retrievedBalance = accountService.getBalance(accountId);

        assertNotNull(retrievedBalance);
    }

    @Test
    void getBalanceNotFound_Test() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getBalance(accountId));
    }

    @Test
    void transferAmountSuccess_Test() throws AccountNotFoundException {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("500.00");

        Account fromAccount = AccountTest.getAccountWithBigAmount();
        Account toAccount = AccountTest.getAccountWithZeroAmount();

        when(accountRepository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(toAccountId)).thenReturn(Optional.of(toAccount));

        TransferResult result = accountService.transferAmount(fromAccountId, toAccountId, amount);

        assertEquals(TransferResult.SUCCESS, result);
        assertEquals(new BigDecimal("500.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("500.00"), toAccount.getBalance());
        verify(accountRepository, times(2)).save(any());
    }
    @Test
    void transferAmountFromAccountNotFound_Test() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("500.00");

        when(accountRepository.findById(fromAccountId)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.transferAmount(fromAccountId, toAccountId, amount));
    }


}
