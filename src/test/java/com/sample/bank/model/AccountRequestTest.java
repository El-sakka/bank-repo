package com.sample.bank.model;

import java.math.BigDecimal;

public class AccountRequestTest {

    public static AccountRequest getAccountRequest(){
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCustomerId(1L);
        accountRequest.setAmount(BigDecimal.valueOf(1000.0));
        return accountRequest;
    }


}
