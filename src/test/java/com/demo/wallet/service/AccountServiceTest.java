package com.demo.wallet.service;

import com.demo.wallet.App;
import com.demo.wallet.controller.exception.AccountDuplicationException;
import com.demo.wallet.controller.exception.AccountNotFoundException;
import com.demo.wallet.controller.message.CheckBalanceReq;
import com.demo.wallet.controller.message.CheckBalanceResp;
import com.demo.wallet.controller.message.RegisterReq;
import com.demo.wallet.controller.message.RegisterResp;
import com.demo.wallet.repository.Account;
import com.demo.wallet.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static com.demo.wallet.service.AccountService.INITIAL_BALANCE_FOR_NEW_ACCOUNT;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, AccountService.class})
@Transactional
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    String EMAIL_A = "a@x.com";
    String EMAIL_B = "b@x.com";

    @Test
    public void testRegisterNewAccountSuccessfully() throws Exception {
        // register new account A
        Account accountA = accountService.registerNewAccount(EMAIL_A);
        assertEquals(INITIAL_BALANCE_FOR_NEW_ACCOUNT, accountA.getBalance());

        Account actualA = accountRepository.findByEmail(EMAIL_A);
        assertNotNull(actualA);
        assertEquals(EMAIL_A, actualA.getEmail());
        assertEquals(INITIAL_BALANCE_FOR_NEW_ACCOUNT, actualA.getBalance());

        // register new account B
        Account accountB = accountService.registerNewAccount(EMAIL_B);
        assertEquals(INITIAL_BALANCE_FOR_NEW_ACCOUNT, accountB.getBalance());

        Account actualB = accountRepository.findByEmail(EMAIL_B);
        assertNotNull(actualB);
        assertEquals(EMAIL_B, actualB.getEmail());
        assertEquals(INITIAL_BALANCE_FOR_NEW_ACCOUNT, actualB.getBalance());
    }

    @Test
    public void testRegisterDuplicatedAccount() throws Exception {
        Account accountA = accountService.registerNewAccount(EMAIL_A);
        assertEquals(INITIAL_BALANCE_FOR_NEW_ACCOUNT, accountA.getBalance());

        try {
            accountService.registerNewAccount(EMAIL_A);
            fail("Should throw Account Duplication Exception");
        } catch (Exception e) {
            assertTrue(e instanceof AccountDuplicationException);
        }
    }

    @Test
    public void testGetBalanceWithNonExistingAccount() throws Exception {
        accountService.registerNewAccount(EMAIL_A);
        BigDecimal actual = accountService.getBalance(EMAIL_A);
        assertEquals(INITIAL_BALANCE_FOR_NEW_ACCOUNT, actual);
    }

    @Test
    public void testGetBalanceWithExistingAccount() throws Exception {
        try {
            accountService.getBalance("@x.com");
            fail("Should throw Account Not Found Exception");
        } catch (Exception e) {
            assertTrue(e instanceof AccountNotFoundException);
        }
    }

}