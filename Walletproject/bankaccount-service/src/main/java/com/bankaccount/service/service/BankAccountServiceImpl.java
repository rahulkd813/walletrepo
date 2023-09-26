package com.bankaccount.service.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankaccount.service.exception.NotFoundException;
import com.bankaccount.service.model.BankAccount;
import com.bankaccount.service.repository.BankAccountRepository;


@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount updateBankAccount(BankAccount bankAccount) throws NotFoundException {
        Optional<BankAccount> existingAccount = bankAccountRepository.findById(bankAccount.getId());

        if (!existingAccount.isPresent()) {
            throw new NotFoundException("Bank Account not found");
        }

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount findBankAccountById(Long bankAccountId) throws NotFoundException {
        Optional<BankAccount> existingAccount = bankAccountRepository.findById(bankAccountId);

        if (!existingAccount.isPresent()) {
            throw new NotFoundException("Bank Account not found with the provided id");
        }

        return existingAccount.get();
    }

    @Override
    public List<BankAccount> findAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        
        bankAccounts.sort(Comparator.comparing(BankAccount::getAccountHolderName));

        return bankAccounts;
    }


    @Override
    public BankAccount deleteBankAccountById(long bankAccountId) throws NotFoundException {
        Optional<BankAccount> existingAccount = bankAccountRepository.findById(bankAccountId);

        existingAccount.ifPresent(account -> {
            bankAccountRepository.delete(account);
        });

        return existingAccount.orElseThrow(() -> new NotFoundException("Bank Account not found"));
    }

}
