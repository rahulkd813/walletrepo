package com.transaction.service.controllerest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.transaction.service.controller.TransactionController;
import com.transaction.service.dto.AddMoneyRequest;
import com.transaction.service.exception.AuthenticationFailedException;
import com.transaction.service.exception.InvalidAccountIdException;
import com.transaction.service.exception.InvalidAmountException;
import com.transaction.service.exception.NotFoundException;
import com.transaction.service.model.Transaction;
import com.transaction.service.service.TransactionService;

@RunWith(SpringRunner.class)

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;
    private AddMoneyRequest addMoneyRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transaction = new Transaction();
        

        addMoneyRequest = new AddMoneyRequest();
       
    }

    @Test
    void testSendTransaction_Success() throws InvalidAccountIdException, AuthenticationFailedException, InvalidAmountException {
        when(transactionService.sendingtransaction(any(Transaction.class), anyLong(), anyInt())).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.sendTransaction(transaction, 1L, 1234);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testReceiveTransaction_Success() {
        when(transactionService.receivingtransaction(any(Transaction.class), anyLong())).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.receiveTransaction(transaction, 2L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testUpdateTransaction_Success() throws NotFoundException {
        when(transactionService.updateTransaction(any(Transaction.class))).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.updateTransaction(transaction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testUpdateTransaction_NotFound() throws NotFoundException {
        when(transactionService.updateTransaction(any(Transaction.class))).thenThrow(new NotFoundException("Transaction not found"));

        ResponseEntity<Transaction> response = transactionController.updateTransaction(transaction);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetTransactionById_Success() throws NotFoundException {
        when(transactionService.findTransactionById(anyLong())).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testGetTransactionById_NotFound() throws NotFoundException {
        when(transactionService.findTransactionById(anyLong())).thenThrow(new NotFoundException("Transaction not found"));

        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllTransactionsByWalletAccountId_Success() throws NotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        when(transactionService.findAllTransactionByWalletAccountId(anyLong())).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactionsByWalletAccountId(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
    }

    @Test
    void testGetAllTransactionsByWalletAccountId_NotFound() throws NotFoundException {
        when(transactionService.findAllTransactionByWalletAccountId(anyLong())).thenThrow(new NotFoundException("Transactions not found"));

        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactionsByWalletAccountId(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteTransactionById_Success() throws NotFoundException {
        when(transactionService.deleteTransactionBytransactionId(anyLong())).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.deleteTransactionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testDeleteTransactionById_NotFound() throws NotFoundException {
        when(transactionService.deleteTransactionBytransactionId(anyLong())).thenThrow(new NotFoundException("Transaction not found"));

        ResponseEntity<Transaction> response = transactionController.deleteTransactionById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddMoneyFromBank_Success() throws InvalidAmountException, InvalidAccountIdException, AuthenticationFailedException {
        when(transactionService.addmoneyfrombank(any(AddMoneyRequest.class))).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.addMoneyFromBank(addMoneyRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    void testAddMoneyFromBank_InvalidInput() throws InvalidAmountException, InvalidAccountIdException, AuthenticationFailedException {
        when(transactionService.addmoneyfrombank(any(AddMoneyRequest.class))).thenThrow(new InvalidAmountException("Invalid amount"));

        ResponseEntity<Transaction> response = transactionController.addMoneyFromBank(addMoneyRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

