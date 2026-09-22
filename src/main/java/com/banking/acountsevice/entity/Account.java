package com.banking.acountsevice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name="account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account{

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private int id;

    @Column(nullable=false,unique=true)
    private String accountNumber;

    @Column(nullable=false)
    private String accountHolderName;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String phone;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(nullable=false,precision = 15,scale=2)
    private BigDecimal balance;

    @Column(nullable=false,precision=15,scale=2)
    private BigDecimal  dailyTransactionLimit;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}