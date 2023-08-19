package com.sample.bank.model.dto;

import com.sample.bank.model.entity.Customer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private List<AccountDTO> accounts;

}