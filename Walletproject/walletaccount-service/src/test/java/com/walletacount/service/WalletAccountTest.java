package com.walletacount.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.walletaccount.service.exceptions.AlreadyExistException;
import com.walletaccount.service.exceptions.InvalidCredentialsException;
import com.walletaccount.service.exceptions.NotFoundException;
import com.walletaccount.service.model.WalletAccount;
import com.walletaccount.service.repository.WalletAccountRepository;
import com.walletaccount.service.service.WalletAccountServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class WalletAccountTest {

    @Mock
    private WalletAccountRepository walletAccountRepository;

    @InjectMocks
    private WalletAccountServiceImpl walletAccountService;

    @Test
    void testSaveWalletAccountWhenEmailDoesNotExist() throws AlreadyExistException {
        WalletAccount testAccount = new WalletAccount();
        testAccount.setEmailAddress("rahulexample.com");

        when(walletAccountRepository.findByEmailAddress(testAccount.getEmailAddress())).thenReturn(Optional.empty());
        when(walletAccountRepository.save(any())).thenReturn(testAccount);

        WalletAccount savedAccount = walletAccountService.saveWalletAccount(testAccount);

        assertEquals(testAccount, savedAccount);
        verify(walletAccountRepository, times(1)).findByEmailAddress(testAccount.getEmailAddress());
        verify(walletAccountRepository, times(1)).save(testAccount);
    }

    @Test
    void testSaveWalletAccountWhenEmailExists() {
        WalletAccount existingAccount = new WalletAccount();
        existingAccount.setEmailAddress("test@example.com");

        when(walletAccountRepository.findByEmailAddress(existingAccount.getEmailAddress())).thenReturn(Optional.of(existingAccount));

        assertThrows(AlreadyExistException.class, () -> walletAccountService.saveWalletAccount(existingAccount));
        verify(walletAccountRepository, times(1)).findByEmailAddress(existingAccount.getEmailAddress());
        verify(walletAccountRepository, never()).save(existingAccount);
    }
    @Test
    public void testFindWalletAccountById() {
        Long walletAccountId = 1L;
        
        WalletAccount mockAccount = new WalletAccount();
        mockAccount.setId(walletAccountId);
        mockAccount.setFullName("John Doe");
        when(walletAccountRepository.findById(walletAccountId)).thenReturn(Optional.of(mockAccount));
        try {
            WalletAccount foundAccount = walletAccountService.findWalletAccountById(walletAccountId);
            assertNotNull(foundAccount);
            assertEquals(walletAccountId, foundAccount.getId());
        } catch (NotFoundException e) {
            fail("NotFoundException should not have been thrown");
        }
    }
    @Test
    public void testDeleteWalletAccountById_ExistingAccount() throws NotFoundException {
        
        long walletAccountId = 1L;
        WalletAccount existingAccount = new WalletAccount();
        existingAccount.setId(walletAccountId);
        when(walletAccountRepository.findById(walletAccountId)).thenReturn(Optional.of(existingAccount));

       
        WalletAccount deletedAccount = walletAccountService.deleteWalletAccountById(walletAccountId);

        
        assertNotNull(deletedAccount);
        assertEquals(walletAccountId, deletedAccount.getId());
        verify(walletAccountRepository, times(1)).delete(existingAccount);
    }
    @Test
    public void testResetPassword_InvalidEmail() {
        // Arrange
        String email = "user@example.com";
        Long walletId = 1L;
        String newPassword = "newPassword";

        when(walletAccountRepository.findByEmailAddress(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> walletAccountService.resetpassword(email, walletId, newPassword));
        verify(walletAccountRepository, never()).save(any());
    }
    
    
    }