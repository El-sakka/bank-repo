package com.sample.bank.utils;

import com.sample.bank.exception.CustomerNotFoundException;
import com.sample.bank.model.entity.Account;
import com.sample.bank.model.entity.Customer;
import com.sample.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class BankUtils {

    public static boolean validateBalance(Account account, BigDecimal amount) {
        return account != null && account.getBalance().compareTo(amount) >= 0;
    }

    public static boolean validateAccountsId(Long fromAccountId, Long toAccountId){
        return fromAccountId.equals(toAccountId);
    }


}
