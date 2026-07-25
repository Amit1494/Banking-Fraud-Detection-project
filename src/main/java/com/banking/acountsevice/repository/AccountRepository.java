package com.banking.acountsevice.repository;

import com.banking.acountsevice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account,String> {
}
