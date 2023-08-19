package com.sample.bank.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferHistoryDTO {
    private Long id;
    private AccountDTO fromAccount;
    private AccountDTO toAccount;
    private BigDecimal amount;
}