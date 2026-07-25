package com.banking.acountsevice.dto;

import com.banking.acountsevice.entity.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    @NotBlank(message="Account Holder name is Required")
    private String accountHolderName;

    @NotBlank(message="EMail required")
    @Email(message="Invalid Email")
    private String email;

    @NotBlank(message="Phone is Required")
    private String phone;

    @NotBlank(message="Account Type required")
    private AccountType accountType;


    @NotNull(message="Initial deposity is required")
    @Positive(message="Initial deposit must be positive")
    private BigDecimal initialDeposit;


}
