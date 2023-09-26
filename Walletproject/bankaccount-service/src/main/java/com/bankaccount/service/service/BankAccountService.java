package com.bankaccount.service.service;

import java.util.List;

import com.bankaccount.service.exception.NotFoundException;
import com.bankaccount.service.model.BankAccount;



public interface BankAccountService {

	public BankAccount saveBankAccount(BankAccount bankAccount);

	   public BankAccount updateBankAccount(BankAccount bankAccount)throws NotFoundException;
		
	   public BankAccount findBankAccountById(Long bankAccountid)throws NotFoundException;
		
		public List<BankAccount> findAllBankAccounts();
		
		public BankAccount deleteBankAccountById(long bankAccountid)throws NotFoundException;
		
	
}
