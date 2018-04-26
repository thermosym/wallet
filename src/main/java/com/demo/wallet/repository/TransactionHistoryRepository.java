package com.demo.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    List<TransactionHistory> findByDebitAccountAndCreditStatus(String debitAccount, TransactionStatus creditStatus);

}
