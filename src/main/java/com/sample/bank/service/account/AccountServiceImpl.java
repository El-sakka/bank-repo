package com.sample.bank.service.account;

import com.sample.bank.exception.AccountNotFoundException;
import com.sample.bank.exception.CustomerNotFoundException;
import com.sample.bank.mapper.AccountMapper;
import com.sample.bank.model.dto.AccountDTO;
import com.sample.bank.model.entity.Account;
import com.sample.bank.model.entity.Customer;
import com.sample.bank.model.entity.TransferHistory;
import com.sample.bank.model.enums.TransferResult;
import com.sample.bank.repository.AccountRepository;
import com.sample.bank.repository.CustomerRepository;
import com.sample.bank.repository.TransferRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.sample.bank.utils.BankUtils.validateBalance;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransferRepository transferRepository;

    AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);;

    @Override
    public AccountDTO createAccount(Long customerId, BigDecimal initDeposit) {
        Customer customer = validateCustomer(customerId);
        Account account = new  Account();
        account.setCustomer(customer);
        account.setBalance(initDeposit);
        account = accountRepository.save(account);
        return accountMapper.toAccountDTO(account);
    }

    @Override
    public AccountDTO getAccount(Long id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));
        return accountMapper.toAccountDTO(account);
    }

    @Override
    public BigDecimal getBalance(Long id) throws AccountNotFoundException{
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));
        return account.getBalance();
    }

    @Override
    @Transactional
    public TransferResult transferAmount(Long fromAccountId, Long toAccountId, BigDecimal amount) throws AccountNotFoundException {
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + fromAccountId));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + toAccountId));
        if (!validateBalance(fromAccount, amount)) {
            return TransferResult.INSUFFICIENT_BALANCE;
        }
        return triggerTransaction(fromAccount,toAccount,amount);
    }

    @Override
    public List<TransferHistory> getTransferHistory(Long id) {
        return transferRepository.findByFromAccountIdOrToAccountId(id);
    }

    private TransferResult triggerTransaction(Account fromAccount, Account toAccount, BigDecimal amount){
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        TransferHistory transferHistory = new TransferHistory(fromAccount,toAccount,amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        transferRepository.save(transferHistory);
        return TransferResult.SUCCESS;
    }
    private Customer validateCustomer(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()){
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
        return customer.get();
    }

}
