package com.transaction.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.service.model.WalletAccount;


@Repository
public interface WalletAccountRepository extends JpaRepository<WalletAccount, Long>{

}