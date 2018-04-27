package com.demo.wallet.service;


import com.demo.wallet.controller.exception.AccountNotExistingException;
import com.demo.wallet.repository.TransactionHistory;
import com.demo.wallet.repository.TransactionHistoryRepository;
import com.demo.wallet.repository.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CreditTransactionProcessor {
    private static final Logger log = LoggerFactory.getLogger(CreditTransactionProcessor.class);

    @Autowired
    private TransactionManagement transactionManagement;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Transactional
    public void processCreditStep(TransactionHistory transactionHistory) {
        boolean added = transactionManagement.addBalance(transactionHistory.getCreditAccount(), transactionHistory.getAmount());
        if (!added) {
            log.error("Transferee {} not found for the transaction {}", transactionHistory.getCreditAccount(), transactionHistory);
            revertBackDebitStep(transactionHistory);
        } else {
            Optional<TransactionHistory> history = transactionHistoryRepository.findById(transactionHistory.getId());
            if (history.isPresent()) {
                TransactionHistory txHistory = history.get();
                if (txHistory.getCreditStatus() == TransactionStatus.NEW) {
                    // record credit processed
                    txHistory.setCreditStatus(TransactionStatus.PROCESSED);
                    transactionHistoryRepository.save(txHistory);
                } else {
                    log.error("Already reverted, duplicated");
                    throw new RuntimeException("Wrong debit status");
                }
            }
        }
    }

    public void revertBackDebitStep(TransactionHistory transactionHistory) {
        transactionManagement.addBalance(transactionHistory.getDebitAccount(), transactionHistory.getAmount());
        Optional<TransactionHistory> history = transactionHistoryRepository.findById(transactionHistory.getId());
        if (history.isPresent()) {
            TransactionHistory txHistory = history.get();
            if (txHistory.getDebitStatus() == TransactionStatus.PROCESSED) {
                // record revert back
                txHistory.setDebitStatus(TransactionStatus.REVERTED);
                txHistory.setCreditStatus(TransactionStatus.REVERTED);
                transactionHistoryRepository.save(txHistory);
            } else {
                log.error("Already reverted, duplicated");
                throw new RuntimeException("Wrong debit status");
            }
        }
    }

}
