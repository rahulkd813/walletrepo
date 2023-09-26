package com.transaction.service.repositorytest;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.transaction.service.model.Transaction;
import com.transaction.service.repository.TransactionRepository;

@RunWith(SpringRunner.class)

public class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllByWalletAccountId() {
       
        Long walletAccountId = 1L;
        List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository.findAllByWalletAccountId(walletAccountId)).thenReturn(transactions);
    }

    @Test
    void testFindById() {
       
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
    }

    @Test
    void testSave() {
        
        Transaction transactionToSave = new Transaction();
        Transaction savedTransaction = new Transaction();
        when(transactionRepository.save(transactionToSave)).thenReturn(savedTransaction);
    }

}
