package com.transaction.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.service.model.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	List<Transaction> findAllByWalletAccountId(Long walletAccountid);


}