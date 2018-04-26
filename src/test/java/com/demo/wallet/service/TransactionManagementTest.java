package com.demo.wallet.service;

import com.demo.wallet.App;
import com.demo.wallet.repository.Account;
import com.demo.wallet.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, TransactionManagement.class})
@Transactional
public class TransactionManagementTest {

    @Autowired
    TransactionManagement transactionManagement;

    @Autowired
    AccountRepository accountRepository;

    String EMAIL_A = "a@x.com";
    String EMAIL_NOT_FOUND = "NotFound@x.com";

    private void setupAccountA() {
        Account accountA = new Account(EMAIL_A, new BigDecimal(100));
        accountRepository.save(accountA);
    }

    @Test
    public void testDeductBalanceSuccess() {
        setupAccountA();

        boolean deducted = transactionManagement.deductBalance(EMAIL_A, new BigDecimal(1));
        assertTrue(deducted);
        assertEquals(99, accountRepository.findByEmail(EMAIL_A).getBalance().intValue());
    }

    @Test
    public void testDeductBalanceWithInsufficientBalance() {
        setupAccountA();

        boolean deducted = transactionManagement.deductBalance(EMAIL_A, new BigDecimal(101));
        assertFalse(deducted);
        assertEquals(100, accountRepository.findByEmail(EMAIL_A).getBalance().intValue());
    }

    @Test
    public void testDeductBalanceWithNonExistingAccount() {
        setupAccountA();

        boolean deducted = transactionManagement.deductBalance(EMAIL_NOT_FOUND, new BigDecimal(1));
        assertFalse(deducted);
    }

    @Test
    public void testAddBalanceSuccessfully() {
        setupAccountA();
        boolean added = transactionManagement.addBalance(EMAIL_A, BigDecimal.ONE);
        assertTrue(added);
        assertEquals(101, accountRepository.findByEmail(EMAIL_A).getBalance().intValue());
    }

    @Test
    public void testAddBalanceWithNonExistingAccount() {
        setupAccountA();
        boolean added = transactionManagement.addBalance(EMAIL_NOT_FOUND, BigDecimal.ONE);
        assertFalse(added);
    }
}