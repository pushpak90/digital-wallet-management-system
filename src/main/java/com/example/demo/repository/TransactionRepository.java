package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,  Long>{
    List<Transaction> findByFromWalletWalletIdOrToWalletWalletId(
            Long fromWalletId,
            Long toWalletId
    );    
}
