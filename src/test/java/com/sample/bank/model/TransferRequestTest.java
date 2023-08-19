package com.sample.bank.model;

import java.math.BigDecimal;

public class TransferRequestTest {

    public static TransferRequest getTransferRequest(){
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(1L);
        transferRequest.setToAccountId(2L);
        transferRequest.setAmount(BigDecimal.valueOf(100.0));
        return transferRequest;
    }
}
