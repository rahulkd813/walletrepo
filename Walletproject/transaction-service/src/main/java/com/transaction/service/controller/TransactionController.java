package com.transaction.service.controller;

import com.transaction.service.dto.AddMoneyRequest;

import com.transaction.service.exception.AuthenticationFailedException;
import com.transaction.service.exception.InvalidAccountIdException;
import com.transaction.service.exception.InvalidAmountException;
import com.transaction.service.exception.NotFoundException;
import com.transaction.service.model.Transaction;
import com.transaction.service.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/sendmoney")
    public ResponseEntity<Transaction> sendTransaction(@RequestBody Transaction transaction,
                                                       @RequestParam Long walletAccountId,
                                                       @RequestParam int walletPin) throws InvalidAccountIdException,AuthenticationFailedException,InvalidAmountException{
        Transaction sentTransaction = transactionService.sendingtransaction(transaction, walletAccountId, walletPin);
        return new ResponseEntity<>(sentTransaction, HttpStatus.CREATED);
    }

    @PostMapping("/receivemoney")
    public ResponseEntity<Transaction> receiveTransaction(@RequestBody Transaction transaction,
                                                          @RequestParam Long receivingWalletAccountId) {
        Transaction receivedTransaction = transactionService.receivingtransaction(transaction, receivingWalletAccountId);
        return new ResponseEntity<>(receivedTransaction, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(transaction);
            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/gettransaction")
    public ResponseEntity<Transaction> getTransactionById(@RequestParam Long transactionId) {
        try {
            Transaction transaction = transactionService.findTransactionById(transactionId);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getalltransactions")
    public ResponseEntity<List<Transaction>> getAllTransactionsByWalletAccountId(@RequestParam Long walletAccountId) {
        try {
            List<Transaction> transactions = transactionService.findAllTransactionByWalletAccountId(walletAccountId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/transaction")
    public ResponseEntity<Transaction> deleteTransactionById(@RequestParam Long transactionId) {
        try {
            Transaction deletedTransaction = transactionService.deleteTransactionBytransactionId(transactionId);
            return new ResponseEntity<>(deletedTransaction, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addmoneyfrombank")
    public ResponseEntity<Transaction> addMoneyFromBank(@RequestBody AddMoneyRequest addMoneyRequest) {
        try {
            Transaction addedMoneyTransaction = transactionService.addmoneyfrombank(addMoneyRequest);
            return new ResponseEntity<>(addedMoneyTransaction, HttpStatus.CREATED);
        } catch (InvalidAmountException | InvalidAccountIdException | AuthenticationFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
