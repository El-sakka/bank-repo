package com.sample.bank.service.customer;

import com.sample.bank.exception.CustomerNotFoundException;
import com.sample.bank.mapper.CustomerMapper;
import com.sample.bank.model.CustomerRequest;
import com.sample.bank.model.dto.CustomerDTO;
import com.sample.bank.model.entity.Customer;
import com.sample.bank.repository.CustomerRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);


    @Override
    public CustomerDTO createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer = customerRepository.save(customer);
        return customerMapper.toCustomerDTO(customer);
    }

    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException{
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        return customerMapper.toCustomerDTO(customer);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerRequest request) throws CustomerNotFoundException{
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        customer.setName(request.getName());
        customer = customerRepository.save(customer);
        return customerMapper.toCustomerDTO(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }


}
