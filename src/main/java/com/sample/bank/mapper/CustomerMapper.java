package com.sample.bank.mapper;


import com.sample.bank.model.dto.CustomerDTO;
import com.sample.bank.model.entity.Customer;
import com.sample.bank.service.account.AccountServiceImpl;
import com.sample.bank.service.customer.CustomerServiceImp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
public interface CustomerMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    CustomerDTO toCustomerDTO(Customer customer);
}
