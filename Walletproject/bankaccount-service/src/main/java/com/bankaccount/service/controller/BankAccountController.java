package com.bankaccount.service.controller;

import com.bankaccount.service.exception.NotFoundException;
import com.bankaccount.service.model.BankAccount;
import com.bankaccount.service.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bankaccount")
@CrossOrigin
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
        return new ResponseEntity<>(bankAccountService.saveBankAccount(bankAccount), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BankAccount> updateBankAccount(@RequestBody BankAccount bankAccount) throws NotFoundException {
        BankAccount updatedAccount = bankAccountService.updateBankAccount(bankAccount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<BankAccount> getBankAccountById(@RequestParam Long id) throws NotFoundException {
        BankAccount account = bankAccountService.findBankAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() throws NotFoundException {
        List<BankAccount> accounts = bankAccountService.findAllBankAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BankAccount> deleteBankAccountById(@RequestParam Long id) throws NotFoundException {
        BankAccount deletedAccount = bankAccountService.deleteBankAccountById(id);
        return new ResponseEntity<>(deletedAccount, HttpStatus.OK);
    }
}
