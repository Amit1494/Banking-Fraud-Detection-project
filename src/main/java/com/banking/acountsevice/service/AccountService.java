package com.banking.acountsevice.service;

import com.banking.acountsevice.dto.AccountResponse;
import com.banking.acountsevice.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

public class AccountService {
    public @Nullable AccountResponse createAccount(@Valid CreateAccountRequest request) {
    }

    public @Nullable AccountResponse getAccount(String accountNumber) {
    }

    public @Nullable BigDecimal getBalance(String accountNumber) {
        return null;
    }

    public void blockAccount(String accountNumber) {
    }

    public void deductBalance(String accountNumber, BigDecimal amount) {
    }
}
