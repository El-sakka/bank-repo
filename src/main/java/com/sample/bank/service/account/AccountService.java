package com.sample.bank.service.account;

import com.sample.bank.exception.AccountNotFoundException;
import com.sample.bank.model.dto.AccountDTO;
import com.sample.bank.model.entity.Account;
import com.sample.bank.model.entity.Customer;
import com.sample.bank.model.entity.TransferHistory;
import com.sample.bank.model.enums.TransferResult;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public AccountDTO createAccount(Long customer, BigDecimal initDeposit);

    public AccountDTO getAccount(Long id) throws AccountNotFoundException;

    public BigDecimal getBalance(Long id) throws AccountNotFoundException;

    public TransferResult transferAmount(Long fromAccountId, Long toAccountId, BigDecimal amount);

    public List<TransferHistory> getTransferHistory(Long id);
}
