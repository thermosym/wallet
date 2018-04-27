package com.demo.wallet.service;

import com.demo.wallet.controller.exception.AccountNotExistingException;
import com.demo.wallet.controller.exception.BalanceInsufficientException;
import com.demo.wallet.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.demo.wallet.util.ThreadUtil.newBoundedFixedThreadPool;

@Service
public class TransferService {
    private static final Logger log = LoggerFactory.getLogger(TransferService.class);
    public static final String TRANSFER_TYPE = "transfer";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionManagement transactionManagement;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private CreditTransactionProcessor creditTransactionProcessor;

    private ExecutorService es = newBoundedFixedThreadPool(2, 10);

    @Transactional(rollbackOn = Throwable.class)
    public void processBalanceTransfer(String debitAccountEmail, String creditAccountEmail, BigDecimal amount)
            throws AccountNotExistingException, BalanceInsufficientException {
        Account debitAccount = accountRepository.findByEmail(debitAccountEmail);
        Account creditAccount = accountRepository.findByEmail(creditAccountEmail);

        if (debitAccount == null || creditAccount == null) {
            throw new AccountNotExistingException("Either Account or Transferee not found");
        }

        if (debitAccount.getBalance().compareTo(amount) < 0) {
            throw new BalanceInsufficientException("Account " + debitAccountEmail + " balance insufficient");
        }

        boolean deducted = transactionManagement.deductBalance(debitAccountEmail, amount);
        if (!deducted) {
            throw new BalanceInsufficientException("Account " + debitAccountEmail + " balance insufficient");
        }

//        boolean added = transactionManagement.addBalance(creditAccountEmail, amount);
//        if (!added) {
//            throw new AccountNotExistingException("Transferee " + creditAccountEmail + " not found");
//        }

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(amount);
        transactionHistory.setDebitAccount(debitAccountEmail);
        transactionHistory.setCreditAccount(creditAccountEmail);
        transactionHistory.setTransactionType(TRANSFER_TYPE);
        transactionHistory.setExecutionTime(new Timestamp(System.currentTimeMillis()));
        transactionHistory.setDebitStatus(TransactionStatus.PROCESSED);
        transactionHistory.setCreditStatus(TransactionStatus.NEW);

        TransactionHistory savedTransactionHistory = transactionHistoryRepository.save(transactionHistory);
        es.submit(() -> {
            try {
                creditTransactionProcessor.processCreditStep(savedTransactionHistory);
            } catch (Exception e) {
                log.error("Error on processing credit step", e);
            }
        });
    }

    public List<TransactionHistory> getTransactionHistory(String email) throws AccountNotExistingException {
        if (accountRepository.findByEmail(email) == null) {
            throw new AccountNotExistingException("Account " + email + " not found");
        }

        List<TransactionHistory> history = transactionHistoryRepository.findByDebitAccountAndDebitStatusAndCreditStatus(email, TransactionStatus.PROCESSED, TransactionStatus.PROCESSED);
        return history;
    }
}
