package com.sample.bank.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "آName is required")
    private String name;
}
