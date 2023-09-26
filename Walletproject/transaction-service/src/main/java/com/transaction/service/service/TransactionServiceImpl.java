package com.transaction.service.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.service.dto.AddMoneyRequest;
import com.transaction.service.dto.BankAccountDTO;
import com.transaction.service.exception.AuthenticationFailedException;
import com.transaction.service.exception.InvalidAccountIdException;
import com.transaction.service.exception.InvalidAmountException;
import com.transaction.service.exception.NotFoundException;
import com.transaction.service.model.BankAccount;
import com.transaction.service.model.Transaction;
import com.transaction.service.model.WalletAccount;
import com.transaction.service.repository.BankAccountRepository;
import com.transaction.service.repository.TransactionRepository;
import com.transaction.service.repository.WalletAccountRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private WalletAccountRepository walletAccountRepo;

    @Autowired
    private BankAccountRepository bankAccountRepo;

    @Override
    public Transaction sendingtransaction(Transaction transaction, Long walletAccountId, int walletPin)
            throws InvalidAccountIdException, AuthenticationFailedException, InvalidAmountException {
        Optional<WalletAccount> senderWalletAccount = walletAccountRepo.findById(walletAccountId);

        if (senderWalletAccount.isEmpty()) {
            throw new InvalidAccountIdException("Sender Wallet Account not found");
        }

        WalletAccount retrievedSenderWalletAccount = senderWalletAccount.get();

        Optional<WalletAccount> receiverWalletAccount = walletAccountRepo.findById(transaction.getSenderorReceiverid());

        if (receiverWalletAccount.isEmpty()) {
            throw new InvalidAccountIdException("Receiver Wallet Account not found");
        }

        if (retrievedSenderWalletAccount.getBalance() < transaction.getAmount() || transaction.getAmount() < 100 || transaction.getAmount() > 100000) {
            throw new InvalidAmountException("Insufficient Balance");
        }
        
        if (retrievedSenderWalletAccount.getWalletPin() != walletPin) {
            throw new AuthenticationFailedException("Authentication Failed Check the Wallet PIN");
        }

        retrievedSenderWalletAccount.setBalance(retrievedSenderWalletAccount.getBalance() - transaction.getAmount());
        transaction.setWalletAccount(retrievedSenderWalletAccount);

        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction receivingtransaction(Transaction transaction, Long receivingWalletAccountId) {
        Optional<WalletAccount> walletAccount = walletAccountRepo.findById(receivingWalletAccountId);

        if (walletAccount.isEmpty()) {
            throw new InvalidAccountIdException("Receiver Wallet Account not found");
        }

        WalletAccount retrievedWalletAccount = walletAccount.get();
        retrievedWalletAccount.setBalance(transaction.getAmount() + retrievedWalletAccount.getBalance());
        walletAccountRepo.save(retrievedWalletAccount);
        transaction.setWalletAccount(retrievedWalletAccount);

        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) throws NotFoundException {
        Optional<Transaction> retrievedTransaction = transactionRepo.findById(transaction.getTransactionid());

        if (retrievedTransaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }

        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction findTransactionById(Long transactionId) throws NotFoundException {
        Optional<Transaction> existingTransaction = transactionRepo.findById(transactionId);

        if (existingTransaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }

        return existingTransaction.get();
    }

    @Override
    public List<Transaction> findAllTransactionByWalletAccountId(Long walletAccountId) throws NotFoundException {
        List<Transaction> transactions = transactionRepo.findAllByWalletAccountId(walletAccountId);

        if (transactions.isEmpty()) {
            throw new NotFoundException("Transactions not found");
        }

        List<Transaction> sortedTransactions = transactions
                .stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .collect(Collectors.toList());

        return sortedTransactions;
    }


    @Override
    public Transaction deleteTransactionBytransactionId(Long transactionId) throws NotFoundException {
        return transactionRepo.findById(transactionId)
                .map(existingTransaction -> {
                    transactionRepo.delete(existingTransaction);
                    return existingTransaction;
                })
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
    }

    @Override
    public Transaction addmoneyfrombank(AddMoneyRequest addMoneyRequest)
            throws InvalidAmountException, InvalidAccountIdException, AuthenticationFailedException {
        Transaction transaction = addMoneyRequest.getTransaction();
        BankAccountDTO bankAccount = addMoneyRequest.getBankAccount();

        Optional<WalletAccount> walletAccount = walletAccountRepo.findById(transaction.getSenderorReceiverid());
        Optional<BankAccount> bank = bankAccountRepo.findByAccountNumber(bankAccount.getAccountNumber());

        if (bank.isEmpty()) {
            throw new InvalidAccountIdException("Bank Account not found");
        }

        BankAccount retrievedBankAccount = bank.get();

        if (!bankAccount.getAccountNumber().equals(retrievedBankAccount.getAccountNumber())
                || !bankAccount.getIfsccode().equals(retrievedBankAccount.getIfsccode())
                || !bankAccount.getAtmCardNumber().equals(retrievedBankAccount.getAtmCardNumber())
                || bankAccount.getAtmPin() != retrievedBankAccount.getAtmPin()) {
            throw new AuthenticationFailedException("Authentication Failed Check the Bank Credentials");
        }

        if (retrievedBankAccount.getBalance() < transaction.getAmount()) {
            throw new InvalidAmountException("Insufficient balance in the bank account Check the Bank Account Balance");
        }

        if (walletAccount.isEmpty() || transaction.getAmount() < 100 || transaction.getAmount() > 100000) {
            throw new InvalidAccountIdException("Wallet Account Not found Check Your Profile to View");
        }

        walletAccount.get().setBalance(transaction.getAmount() + walletAccount.get().getBalance());
        walletAccountRepo.save(walletAccount.get());

        retrievedBankAccount.setBalance(retrievedBankAccount.getBalance() - transaction.getAmount());
        transaction.setWalletAccount(walletAccount.get());

        return transactionRepo.save(transaction);
    }
}