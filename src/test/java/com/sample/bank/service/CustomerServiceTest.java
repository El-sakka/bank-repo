package com.sample.bank.service;


import com.sample.bank.exception.CustomerNotFoundException;
import com.sample.bank.mapper.CustomerMapper;
import com.sample.bank.model.CustomerRequest;
import com.sample.bank.model.CustomerRequestTest;
import com.sample.bank.model.CustomerTest;
import com.sample.bank.model.dto.CustomerDTO;
import com.sample.bank.model.entity.Customer;
import com.sample.bank.repository.CustomerRepository;
import com.sample.bank.service.customer.CustomerServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImp customerService;

    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @Test
    void createCustomer_Test() {
        CustomerRequest request = CustomerRequestTest.getCustomerRequest();

        Customer customer = CustomerTest.getCustomer();

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO customerDTO = customerService.createCustomer(request);

        assertNotNull(customerDTO);
        assertEquals(customer.getId(), customerDTO.getId());
        assertEquals(customer.getName(), customerDTO.getName());
    }

    @Test
    void getCustomer_Test() {
        Long customerId = 1L;
        Customer customer = CustomerTest.getCustomer();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomer(customerId);

        assertNotNull(customerDTO);
        assertEquals(customer.getId(), customerDTO.getId());
    }

    @Test
    void testGetCustomerNotFound() {
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(customerId));
    }

    @Test
    void testUpdateCustomer() {
        Long customerId = 1L;
        CustomerRequest request = CustomerRequestTest.getCustomerRequest();

        Customer customer = CustomerTest.getCustomer();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO updatedCustomerDTO = customerService.updateCustomer(customerId, request);

        assertNotNull(updatedCustomerDTO);
        assertEquals(customer.getId(), updatedCustomerDTO.getId());
        assertEquals(request.getName(), updatedCustomerDTO.getName());
    }

    @Test
    void testUpdateCustomerNotFound() {
        Long customerId = 1L;
        CustomerRequest request = CustomerRequestTest.getCustomerRequest();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(customerId, request));
    }
}
