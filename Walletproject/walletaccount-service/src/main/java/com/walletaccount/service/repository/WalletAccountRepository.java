package com.walletaccount.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletaccount.service.model.WalletAccount;

@Repository
public interface WalletAccountRepository extends JpaRepository<WalletAccount, Long>{

	public Optional<WalletAccount> findByEmailAddress(String emailAddress); 
	

}