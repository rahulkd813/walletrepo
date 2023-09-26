package com.transaction.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.service.model.BankAccount;
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

	public Optional<BankAccount> findByAccountNumber(String accountNumber);

}
