package com.walletaccount.service.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletaccount.service.exceptions.AlreadyExistException;
import com.walletaccount.service.exceptions.InvalidCredentialsException;
import com.walletaccount.service.exceptions.NotFoundException;
import com.walletaccount.service.model.WalletAccount;
import com.walletaccount.service.repository.WalletAccountRepository;
@Service
public class WalletAccountServiceImpl implements WalletAccountService {

    @Autowired
    private WalletAccountRepository walletAccountRepo;

    @Override
    public WalletAccount saveWalletAccount(WalletAccount walletAccount) throws AlreadyExistException {
        Optional<WalletAccount> existingAccount = walletAccountRepo.findByEmailAddress(walletAccount.getEmailAddress());

        if (existingAccount.isPresent()) {
            throw new AlreadyExistException("Email ID already exists");
        }

        return walletAccountRepo.save(walletAccount);
    }

    @Override
    public WalletAccount updateWalletAccount(WalletAccount walletAccount) throws NotFoundException {
        Optional<WalletAccount> existingAccount = walletAccountRepo.findById(walletAccount.getId());

        if (existingAccount.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return walletAccountRepo.save(walletAccount);
    }

    @Override
    public WalletAccount findWalletAccountById(Long walletAccountId) throws NotFoundException {
        Optional<WalletAccount> existingAccount = walletAccountRepo.findById(walletAccountId);

        if (existingAccount.isEmpty()) {
            throw new NotFoundException("User not found with the provided ID");
        }

        return existingAccount.get();
    }

    @Override
    public List<WalletAccount> findAllWalletAccounts() throws NotFoundException {
        List<WalletAccount> existingAccounts = walletAccountRepo.findAll();

        if (existingAccounts.isEmpty()) {
            throw new NotFoundException("Accounts not found");
        }

        return existingAccounts;
    }

    @Override
    public WalletAccount deleteWalletAccountById(long walletAccountId) throws NotFoundException {
        Optional<WalletAccount> existingAccount = walletAccountRepo.findById(walletAccountId);

        if (existingAccount.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        walletAccountRepo.delete(existingAccount.get());
        return existingAccount.get();
    }

    @Override
    public WalletAccount login(String email, String password) throws InvalidCredentialsException {
        return walletAccountRepo.findByEmailAddress(email)
                .filter(account -> account.getPassword().equals(password))
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials!"));
    }

    @Override
    public WalletAccount resetpassword(String email, Long walletId, String newPassword)
            throws InvalidCredentialsException {
        return walletAccountRepo.findByEmailAddress(email)
                .filter(account -> account.getId().equals(walletId))
                .map(account -> {
                    account.setPassword(newPassword);
                    return walletAccountRepo.save(account);
                })
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials! Check the details!"));
    }

}
