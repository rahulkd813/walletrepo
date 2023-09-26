package com.transaction.service.dto;

import com.transaction.service.model.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMoneyRequest {
    private BankAccountDTO bankAccount;
    private Transaction transaction;
    
}
