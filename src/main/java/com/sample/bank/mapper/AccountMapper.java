package com.sample.bank.mapper;


import com.sample.bank.model.dto.AccountDTO;
import com.sample.bank.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "balance", target = "balance")
    AccountDTO toAccountDTO(Account account);
}