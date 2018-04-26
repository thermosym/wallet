package com.demo.wallet.service;

import com.demo.wallet.App;
import com.demo.wallet.controller.exception.AccountNotExistingException;
import com.demo.wallet.controller.exception.BalanceInsufficientException;
import com.demo.wallet.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.demo.wallet.repository.TransactionStatus.PROCESSED;
import static com.demo.wallet.service.TransferService.TRANSFER_TYPE;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, TransferService.class})
@Transactional
public class TransferServiceTest {

    @Autowired
    TransferService transferService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    String EMAIL_A = "a@x.com";
    String EMAIL_B = "b@x.com";
    String EMAIL_NOT_FOUND = "NotFound@x.com";

    @Before
    public void initAccount() {
        Account accountA = new Account(EMAIL_A, new BigDecimal(100));
        accountRepository.save(accountA);

        Account accountB = new Account(EMAIL_B, new BigDecimal(100));
        accountRepository.save(accountB);
    }

    @Test
    public void testProcessBalanceTransferSuccessfully() throws Exception {
        transferService.processBalanceTransfer(EMAIL_A, EMAIL_B, BigDecimal.ONE);
        assertEquals(99, accountRepository.findByEmail(EMAIL_A).getBalance().intValue());
        assertEquals(101, accountRepository.findByEmail(EMAIL_B).getBalance().intValue());
        List<TransactionHistory> historyList = transactionHistoryRepository.findByDebitAccountAndCreditStatus(EMAIL_A, PROCESSED);
        assertEquals(1, historyList.size());
        TransactionHistory actualHistory = historyList.get(0);
        assertEquals(EMAIL_A, actualHistory.getDebitAccount());
        assertEquals(EMAIL_B, actualHistory.getCreditAccount());
        assertEquals(BigDecimal.ONE, actualHistory.getAmount());
        assertEquals(PROCESSED, actualHistory.getDebitStatus());
        assertEquals(PROCESSED, actualHistory.getCreditStatus());
        assertEquals(TRANSFER_TYPE, actualHistory.getTransactionType());
    }

    @Test
    public void testProcessBalanceTransferWhenAccountBalanceInsufficient() throws Exception {
        try {
            transferService.processBalanceTransfer(EMAIL_A, EMAIL_B, new BigDecimal(100.01));
            fail("Balance insufficient should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof BalanceInsufficientException);
        }
        assertEquals(100, accountRepository.findByEmail(EMAIL_A).getBalance().intValue());
        assertEquals(100, accountRepository.findByEmail(EMAIL_B).getBalance().intValue());
        List<TransactionHistory> historyList = transactionHistoryRepository.findByDebitAccountAndCreditStatus(EMAIL_A, PROCESSED);
        assertEquals(0, historyList.size());
    }

    @Test
    public void testProcessBalanceTransferWhenAccountNotFound() throws Exception {
        try {
            transferService.processBalanceTransfer(EMAIL_NOT_FOUND, EMAIL_B, new BigDecimal(100.01));
            fail("Account not found should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof AccountNotExistingException);
        }
        assertEquals(100, accountRepository.findByEmail(EMAIL_B).getBalance().intValue());

        try {
            transferService.processBalanceTransfer(EMAIL_A, EMAIL_NOT_FOUND, new BigDecimal(100.01));
            fail("Account not found should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof AccountNotExistingException);
        }
        assertEquals(100, accountRepository.findByEmail(EMAIL_A).getBalance().intValue());
    }
}