package com.transaction.service.service;

import java.util.List;

import com.transaction.service.dto.AddMoneyRequest;
import com.transaction.service.dto.BankAccountDTO;
import com.transaction.service.exception.AuthenticationFailedException;
import com.transaction.service.exception.InvalidAccountIdException;
import com.transaction.service.exception.InvalidAmountException;
import com.transaction.service.exception.NotFoundException;
import com.transaction.service.model.Transaction;


public interface TransactionService {

	public Transaction sendingtransaction(Transaction transaction,Long walletaccountid,int walletpin)throws InvalidAccountIdException,AuthenticationFailedException,InvalidAmountException;

	public Transaction receivingtransaction(Transaction transaction,Long receivingwalletaccountid);

	   public Transaction updateTransaction(Transaction transaction)throws NotFoundException;
		
	   public Transaction findTransactionById(Long transactionid)throws NotFoundException;
		
		public List<Transaction> findAllTransactionByWalletAccountId(Long walletAccountid)throws NotFoundException;
		
		public Transaction addmoneyfrombank(AddMoneyRequest addMoneyRequest) throws InvalidAmountException,InvalidAccountIdException,AuthenticationFailedException;
		
		public Transaction deleteTransactionBytransactionId(Long transactionid)throws NotFoundException;
		
}
