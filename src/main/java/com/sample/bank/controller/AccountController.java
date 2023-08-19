package com.sample.bank.controller;

import com.sample.bank.model.AccountRequest;
import com.sample.bank.model.TransferRequest;
import com.sample.bank.model.dto.AccountDTO;
import com.sample.bank.model.entity.Customer;
import com.sample.bank.model.entity.TransferHistory;
import com.sample.bank.model.enums.TransferResult;
import com.sample.bank.model.error.ErrorResponse;
import com.sample.bank.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static com.sample.bank.utils.BankUtils.validateAccountsId;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;



    @PostMapping
    @Operation(summary = "Create a new bank account for a customer")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountRequest request) {
        AccountDTO account = accountService.createAccount(request.getCustomerId(), request.getAmount());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.getId())
                .toUri();
        return ResponseEntity.created(location).body(account);
    }

    @GetMapping("/{id}")
    @Operation(summary =  "Retrieve account details by ID")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        AccountDTO account = accountService.getAccount(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("{id}/balance")
    @Operation(summary =  "Retrieve account balance by ID")
    public ResponseEntity<?> getBalance(@PathVariable Long id) {
        BigDecimal balance = accountService.getBalance(id);
        return ResponseEntity.ok(balance);

    }
    @GetMapping("/{accountId}/history")
    @Operation(summary =  "Retrieve account transfer history by ID")
    public ResponseEntity<List<TransferHistory>> getTransferHistory(@PathVariable Long accountId) {
        List<TransferHistory> history = accountService.getTransferHistory(accountId);
        return ResponseEntity.ok(history);
    }
    @PostMapping("/transfer")
    @Operation(summary =  "transfer amount between two accounts")
    public ResponseEntity<?> transferAmount(@Valid @RequestBody TransferRequest request) {

        if(validateAccountsId(request.getFromAccountId(), request.getToAccountId())){
            return ResponseEntity.badRequest().body(new ErrorResponse("Cannot transfer to the same account"));
        }

        TransferResult result = accountService.transferAmount(request.getFromAccountId(), request.getToAccountId(), request.getAmount());

        if(result == TransferResult.INSUFFICIENT_BALANCE){
            return ResponseEntity.badRequest().body(new ErrorResponse("Transfer failed: Insufficient balance"));
        }
        return ResponseEntity.ok(new ErrorResponse("Transfer successful"));
    }

}
