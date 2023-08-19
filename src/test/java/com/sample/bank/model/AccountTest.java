package com.sample.bank.model;

import com.sample.bank.model.dto.AccountDTO;
import com.sample.bank.model.entity.Account;

import java.math.BigDecimal;

public class AccountTest {

    public static Account getAccount(){
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(100.0));
        account.setCustomer(CustomerTest.getCustomer());
        return account;
    }

    public static Account getAccountWithBigAmount(){
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1000.0));
        account.setCustomer(CustomerTest.getCustomer());
        return account;
    }

    public static Account getAccountWithZeroAmount(){
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(0.0));
        account.setCustomer(CustomerTest.getCustomer());
        return account;
    }

    public static AccountDTO getAccountDTO(){
        AccountDTO account = new AccountDTO();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(100.0));
        return account;
    }
}
