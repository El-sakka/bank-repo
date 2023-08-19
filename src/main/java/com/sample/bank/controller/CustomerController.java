package com.sample.bank.controller;

import com.sample.bank.model.CustomerRequest;
import com.sample.bank.model.dto.CustomerDTO;
import com.sample.bank.service.customer.CustomerServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImp customerService;


    /**
     * Create a new customer.
     *
     * @param request The customer details to be created.
     * @return ResponseEntity containing the created customer and a 201 Created status code.
     */
    @PostMapping
    @Operation(summary = "Create a new customer")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerRequest request){
        CustomerDTO customer = customerService.createCustomer(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();

        return ResponseEntity.created(location).body(customer);
    }
    @GetMapping("/{customerId}")
    @Operation(summary = "Retrieve customer details by ID")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId){
        CustomerDTO customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }


    @GetMapping
    @Operation(summary = "Retrieve all customers details")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        List<CustomerDTO> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update customer details by ID and corresponding data")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long customerId,@Valid @RequestBody CustomerRequest request){
        CustomerDTO updatedCustomer = customerService.updateCustomer(customerId, request);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete customer by ID")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
