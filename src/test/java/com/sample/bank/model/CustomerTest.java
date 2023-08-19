package com.sample.bank.model;

import com.sample.bank.model.dto.CustomerDTO;
import com.sample.bank.model.entity.Customer;

public class CustomerTest {

    public static Customer getCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("test");
        return customer;
    }

    public static CustomerDTO getCustomerDTO(){
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1L);
        customer.setName("test");
        return customer;
    }
}
