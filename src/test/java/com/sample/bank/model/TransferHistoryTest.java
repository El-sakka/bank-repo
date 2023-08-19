package com.sample.bank.model;

import com.sample.bank.model.entity.TransferHistory;

public class TransferHistoryTest {

    public static TransferHistory getTransferHistory() {
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setId(1L);
        return transferHistory;
    }
}
