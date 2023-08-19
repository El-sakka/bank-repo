package com.sample.bank.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "transfer_history")
@Data
@NoArgsConstructor
public class TransferHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "From account ID is required")
    @ManyToOne
    @JoinColumn(name = "from_account_id", referencedColumnName = "id")
    private Account fromAccount;

    @NotNull(message = "To account ID is required")
    @ManyToOne
    @JoinColumn(name = "to_account_id", referencedColumnName = "id")
    private Account toAccount;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;


    public TransferHistory(Account fromAccount, Account toAccount, BigDecimal amount){
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }
}
