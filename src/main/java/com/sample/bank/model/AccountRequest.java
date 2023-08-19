package com.sample.bank.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    @Positive(message = "Initial deposit must be a positive value")
    private BigDecimal amount;
}
