package com.bankaccount.service;
import com.bankaccount.service.exception.NotFoundException;
import com.bankaccount.service.model.BankAccount;
import com.bankaccount.service.repository.BankAccountRepository;
import com.bankaccount.service.service.BankAccountServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class BankaccountServiceApplicationTests {
    
	@Mock
    private BankAccountRepository bankAccountRepository;
	
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testSaveBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountHolderName("Tarun Raj");
        when(bankAccountRepository.save(any())).thenReturn(bankAccount);
        BankAccount savedAccount = bankAccountService.saveBankAccount(bankAccount);
        assertNotNull(savedAccount);
        assertEquals(1L, savedAccount.getId());
        assertEquals("Tarun Raj", savedAccount.getAccountHolderName());
    }
    
    @Test
    public void testUpdateBankAccount() throws NotFoundException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountHolderName("Tarun Raj");
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.save(any())).thenReturn(bankAccount);
        BankAccount updatedAccount = bankAccountService.updateBankAccount(bankAccount);
        assertNotNull(updatedAccount);
        assertEquals("Tarun Raj", updatedAccount.getAccountHolderName());
    }
    
    @Test
    public void testFindBankAccountById() throws NotFoundException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountHolderName("Tarun Raj");
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        BankAccount foundAccount = bankAccountService.findBankAccountById(1L);
        assertNotNull(foundAccount);
        assertEquals("Tarun Raj", foundAccount.getAccountHolderName());
    }
    
    @Test
    public void testFindAllBankAccounts() throws NotFoundException {
        List<BankAccount> accountList = new ArrayList<>();
        BankAccount account1 = new BankAccount();
        account1.setId(1L);
        account1.setAccountHolderName("Tarun Raj");
        accountList.add(account1);
        when(bankAccountRepository.findAll()).thenReturn(accountList);
        List<BankAccount> foundAccounts = bankAccountService.findAllBankAccounts();
        assertNotNull(foundAccounts);
        assertEquals(1, foundAccounts.size());
        assertEquals("Tarun Raj", foundAccounts.get(0).getAccountHolderName());
    }
    
    @Test
    public void testDeleteBankAccountById() throws NotFoundException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountHolderName("Tarun Raj");
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        BankAccount deletedAccount = bankAccountService.deleteBankAccountById(1L);
        assertNotNull(deletedAccount);
        assertEquals("Tarun Raj", deletedAccount.getAccountHolderName());
    }
}