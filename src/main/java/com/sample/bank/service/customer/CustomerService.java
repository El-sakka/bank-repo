package com.sample.bank.service.customer;

import com.sample.bank.exception.CustomerNotFoundException;
import com.sample.bank.model.CustomerRequest;
import com.sample.bank.model.dto.CustomerDTO;
import com.sample.bank.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    public CustomerDTO createCustomer(CustomerRequest request);
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;

    public List<CustomerDTO> getCustomers();

    public CustomerDTO updateCustomer(Long id, CustomerRequest request) throws CustomerNotFoundException;
    public void deleteCustomer(Long id);
}
