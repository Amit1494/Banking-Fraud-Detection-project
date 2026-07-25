package com.banking.acountsevice.controller;

import com.banking.acountsevice.dto.AccountResponse;
import com.banking.acountsevice.dto.CreateAccountRequest;
import com.banking.acountsevice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/vi/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid
@RequestBody CreateAccountRequest request){
         return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request))

    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.getAccount(accountNumber));

    }
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.getBalance(accountNumber));

    }

    public ResponseEntity<String> blockAccount(@PathVariable String accountNumber){
accountService.blockAccount(accountNumber);
return ResponseEntity.ok("Account Blocked Successfully ");
    }

    @PutMapping("/{accountNumber}/deduct")
    public ResponseEntity<String> deductBalance(@PathVariable String accountNumber,@RequestParam BigDecimal amount){
        accountService.deductBalance(accountNumber,amount);

        return ResponseEntity.ok("Balance deducted Successfully ");
    }
    public



}
