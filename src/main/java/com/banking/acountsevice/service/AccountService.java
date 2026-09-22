package com.banking.acountsevice.service;

import com.banking.acountsevice.dto.AccountResponse;
import com.banking.acountsevice.dto.CreateAccountRequest;
import com.banking.acountsevice.entity.Account;
import com.banking.acountsevice.entity.AccountStatus;
import com.banking.acountsevice.entity.AccountType;
import com.banking.acountsevice.repository.AccountRepository;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private static SecureRandom sequreRandom=new SecureRandom();



    public AccountResponse createAccount( CreateAccountRequest request) {
        log.info("Creating Account for ",request.getEmail());
        if(accountRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("account already exist "+request.getEmail());
        }
        Account account=new Account();
        account.setAccountHolderName(request.getAccountHolderName());
        account.setAccountType(request.getAccountType());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(request.getInitialDeposit());
        account.setDailyTransactionLimit(
                request.getAccountType()==AccountType.SAVINGS
                ?new BigDecimal("100000"):new BigDecimal("500000")
        );
        Account savedAccount=accountRepository.save(account);
        log.info("Account Created :",savedAccount.getAccountNumber());
        return mapToResponse(savedAccount);
    }

    private AccountResponse mapToResponse(Account account){
        AccountResponse response=new AccountResponse();
        response.setId(String.valueOf(account.getId()));
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountHolderName(account.getAccountHolderName());
        response.setEmail(account.getEmail());
        response.setPhone(account.getPhone());
        response.setStatus(account.getAccountStatus());
        response.setAccountType(account.getAccountType());
        response.setDailyTransactionLimit(account.getDailyTransactionLimit());
        response.setCreatedAt(account.getCreatedAt());
        response.setBalance(account.getBalance());

        return response;

    }
    private String generateAccountNumber(){

        String accountNumber ;
        do{
            long number=sequreRandom.nextLong(1_000_000_000_000L);
            accountNumber=String.format("%012d",number);

        }while(accountRepository.existsByAccountNumber(accountNumber));
return accountNumber;
    }

    public AccountResponse getAccount(String accountNumber) {

        Account account=accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new RuntimeException("Account not found "));
        return mapToResponse(account);
    }

    public  BigDecimal getBalance(String accountNumber) {

        Account account=accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new RuntimeException("Account not found "));
        return account.getBalance();
    }


    public void blockAccount(String accountNumber) {
        log.info("Blocking account:{}",accountNumber);

        Account account=accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new RuntimeException("Account not found "));
        account.setAccountStatus(AccountStatus.BLOCKED);
        accountRepository.save(account);
        log.info("Account Blocked :",accountNumber);
    }

    public void deductBalance(String accountNumber, BigDecimal amount) {
        log.info("Deducting balance from account ",amount);
        Account account=accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new RuntimeException("Account not found "));
        if(account.getAccountStatus()!=AccountStatus.ACTIVE){
            throw new RuntimeException("Account is not active ");
        }
        if(account.getBalance().compareTo(amount)<0){
            throw new RuntimeException("Insufficient funds ");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        log.info("Balance Updated. New Balance" +account.getBalance());
    }

    public void creditBalance(String accountNumber, BigDecimal amount) {
        log.info("Crediting balance from account ",amount);
        Account account=accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new RuntimeException("Account not found "));
        if(account.getAccountStatus()!=AccountStatus.ACTIVE){
            throw new RuntimeException("Account is not active ");
        }
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        log.info("Balance Updated. New Balance" +account.getBalance());


    }
}
