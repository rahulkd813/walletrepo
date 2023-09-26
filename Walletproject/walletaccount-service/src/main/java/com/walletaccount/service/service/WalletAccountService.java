package com.walletaccount.service.service;

import java.util.List;

import com.walletaccount.service.exceptions.AlreadyExistException;
import com.walletaccount.service.exceptions.InvalidCredentialsException;
import com.walletaccount.service.exceptions.NotFoundException;
import com.walletaccount.service.model.WalletAccount;

public interface WalletAccountService {

   public WalletAccount saveWalletAccount(WalletAccount walletAccount)throws AlreadyExistException;

   public WalletAccount updateWalletAccount(WalletAccount walletAccount)throws NotFoundException;
	
   public WalletAccount findWalletAccountById(Long walletAccountid)throws NotFoundException;
	
	public List<WalletAccount> findAllWalletAccounts()throws NotFoundException;
	
	public WalletAccount deleteWalletAccountById(long walletAccountid)throws NotFoundException;
	
	public WalletAccount login(String email,String password)throws InvalidCredentialsException;
	
	public WalletAccount resetpassword(String email,Long walletid,String newpassword) throws InvalidCredentialsException;
}
