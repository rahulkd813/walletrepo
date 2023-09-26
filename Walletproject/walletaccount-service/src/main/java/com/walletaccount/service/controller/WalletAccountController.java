package com.walletaccount.service.controller;


import com.walletaccount.service.dto.LoginData;
import com.walletaccount.service.exceptions.AlreadyExistException;
import com.walletaccount.service.exceptions.InvalidCredentialsException;
import com.walletaccount.service.exceptions.NotFoundException;
import com.walletaccount.service.model.WalletAccount;
import com.walletaccount.service.service.WalletAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/walletaccount")
@CrossOrigin
public class WalletAccountController {

    @Autowired
    private WalletAccountService walletAccountService;

    @PostMapping("/create")
    public ResponseEntity<WalletAccount> createWalletAccount(@RequestBody WalletAccount walletAccount) throws AlreadyExistException {
                   return new ResponseEntity<>(walletAccountService.saveWalletAccount(walletAccount), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<WalletAccount> updateWalletAccount(@RequestBody WalletAccount walletAccount) throws NotFoundException {
       
            WalletAccount updatedAccount = walletAccountService.updateWalletAccount(walletAccount);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);

    }

    @GetMapping("/get")
    public ResponseEntity<WalletAccount> getWalletAccountById(@RequestParam Long id) throws NotFoundException {
      
            WalletAccount account = walletAccountService.findWalletAccountById(id);
            return new ResponseEntity<>(account, HttpStatus.OK);
        
    }

    @GetMapping("/getall")
    public ResponseEntity< List<WalletAccount>> getAllWalletAccounts() throws NotFoundException {
            List<WalletAccount> accounts = walletAccountService.findAllWalletAccounts();
            return new ResponseEntity<>(accounts, HttpStatus.OK);
       
    }

    @DeleteMapping("/delete")
    public ResponseEntity<WalletAccount> deleteWalletAccountById(@RequestParam Long id) throws NotFoundException {
      
            WalletAccount deletedAccount = walletAccountService.deleteWalletAccountById(id);
            return new ResponseEntity<>(deletedAccount, HttpStatus.OK);
      
    }
    
    @PostMapping("/login")
    public ResponseEntity<WalletAccount> login(@RequestBody LoginData logindata) {
      
            WalletAccount loggedInAccount = walletAccountService.login(logindata.getEmail(), logindata.getPassword());
            return new ResponseEntity<WalletAccount>(loggedInAccount, HttpStatus.OK);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<WalletAccount> resetPassword(
            @RequestParam String email, @RequestParam Long walletid, @RequestParam String newpassword) {
        try {
            WalletAccount resetAccount = walletAccountService.resetpassword(email, walletid, newpassword);
            return new ResponseEntity<>(resetAccount, HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
