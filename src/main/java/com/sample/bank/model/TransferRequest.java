package com.sample.bank.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    @NotNull(message = "From account ID is required")
    private Long fromAccountId;
    @NotNull(message = "To account ID is required")
    private Long toAccountId;
    @Positive(message = "Amount must be a positive value")
    private BigDecimal amount;
}
