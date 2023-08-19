package com.sample.bank.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.bank.model.CustomerRequest;
import com.sample.bank.model.CustomerRequestTest;
import com.sample.bank.model.CustomerTest;
import com.sample.bank.model.dto.CustomerDTO;
import com.sample.bank.repository.CustomerRepository;
import com.sample.bank.service.customer.CustomerServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerServiceImp customerService;

    @InjectMocks
    private CustomerController customerController;


    @Test
    public void createCustomer_Test() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        CustomerRequest request = CustomerRequestTest.getCustomerRequest();

        CustomerDTO savedCustomer = CustomerTest.getCustomerDTO();
        when(customerService.createCustomer(request)).thenReturn(savedCustomer);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    public void getCustomer_ValidId() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        CustomerDTO customer = CustomerTest.getCustomerDTO();

        when(customerService.getCustomer(1L)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test"));
    }


    @Test
    public void testUpdateCustomer_ValidId() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        CustomerRequest request = CustomerRequestTest.getCustomerRequest();

        CustomerDTO updatedCustomer = CustomerTest.getCustomerDTO();
        when(customerService.updateCustomer(eq(1L), any(CustomerRequest.class))).thenReturn(updatedCustomer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test"));
    }


    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/1"))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).deleteCustomer(1L);
    }
}
