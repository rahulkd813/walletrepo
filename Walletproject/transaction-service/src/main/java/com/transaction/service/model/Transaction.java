package com.transaction.service.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionid;

    @NotNull(message = "Transaction Type is required")
    private String transactionType;

    @NotNull(message = "Transaction Date is required")
    private LocalDate transactionDate;

    @NotNull(message = "Transaction Time is required")
    private LocalTime transactionTime;
   

    @NotNull(message = "Sender or Receiverid is required")
    private Long senderorReceiverid;
    
   
    @Positive(message = "Amount must be positive")
    private double amount;

    @ManyToOne(fetch =FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "wallet_account_id")
    private WalletAccount walletAccount;
}
