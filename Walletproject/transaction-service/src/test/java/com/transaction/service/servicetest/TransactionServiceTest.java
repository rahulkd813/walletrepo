package com.transaction.service.servicetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.transaction.service.exception.NotFoundException;
import com.transaction.service.model.BankAccount;
import com.transaction.service.model.Transaction;
import com.transaction.service.model.WalletAccount;
import com.transaction.service.repository.BankAccountRepository;
import com.transaction.service.repository.TransactionRepository;
import com.transaction.service.repository.WalletAccountRepository;
import com.transaction.service.service.TransactionServiceImpl;

@RunWith(SpringRunner.class)

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WalletAccountRepository walletAccountRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private WalletAccount senderWalletAccount;
    private WalletAccount receiverWalletAccount;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

       
        senderWalletAccount = new WalletAccount();
        senderWalletAccount.setId(1L);
        senderWalletAccount.setBalance(1000.0);
        senderWalletAccount.setWalletPin(1234);

        receiverWalletAccount = new WalletAccount();
        receiverWalletAccount.setId(2L);
        receiverWalletAccount.setBalance(500.0);

        bankAccount = new BankAccount();
        bankAccount.setAccountNumber("123456789");
        bankAccount.setIfsccode("ABCD1234");
        bankAccount.setAtmCardNumber("567890123");
        bankAccount.setAtmPin(4321);
        bankAccount.setBalance(1500.0);

        transaction = new Transaction();
        transaction.setAmount(200.0);
        transaction.setSenderorReceiverid(2L);
    }


    @Test
    public void testSendingTransaction_Success() {
        when(walletAccountRepository.findById(1L)).thenReturn(Optional.of(senderWalletAccount));
        when(walletAccountRepository.findById(2L)).thenReturn(Optional.of(receiverWalletAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        assertDoesNotThrow(() -> {
            Transaction result = transactionService.sendingtransaction(transaction, 1L, 1234);
            assertEquals(transaction.getAmount(), result.getAmount());
        });
    }

    @Test
    public void testUpdateTransaction_Success() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        assertDoesNotThrow(() -> {
            Transaction updatedTransaction = new Transaction();
            updatedTransaction.setTransactionid(1L);
            updatedTransaction.setAmount(300.0);

            Transaction result = transactionService.updateTransaction(updatedTransaction);
            assertEquals(updatedTransaction.getAmount(), result.getAmount());
        });
    }

    @Test
    public void testUpdateTransaction_NotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            Transaction updatedTransaction = new Transaction();
            updatedTransaction.setTransactionid(1L);
            updatedTransaction.setAmount(300.0);

            transactionService.updateTransaction(updatedTransaction);
        });
    }
}

