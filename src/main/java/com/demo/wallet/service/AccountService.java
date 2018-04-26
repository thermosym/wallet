package com.demo.wallet.service;

import com.demo.wallet.controller.exception.AccountDuplicationException;
import com.demo.wallet.controller.exception.AccountNotExistingException;
import com.demo.wallet.repository.Account;
import com.demo.wallet.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    public static final BigDecimal INITIAL_BALANCE_FOR_NEW_ACCOUNT = new BigDecimal(10_000L);

    @Autowired
    private AccountRepository accountRepository;

    public Account registerNewAccount(String email) throws AccountDuplicationException {
        // check account existence
        if (accountRepository.findByEmail(email) != null) {
            throw new AccountDuplicationException("Account " + email + " already exists");
        }

        Account account = new Account();
        account.setEmail(email);
        account.setBalance(INITIAL_BALANCE_FOR_NEW_ACCOUNT);

        try {
            Account savedAccount = accountRepository.save(account);
            log.info("Account {} register successfully, entity: {}", email, savedAccount);
            return savedAccount;
        }catch (DataIntegrityViolationException e) {
            log.error("Failed to create Account {}", account, e);
            throw new AccountDuplicationException("Account " + email + " already exists");
        }
    }

    public BigDecimal getBalance(String email) throws AccountNotExistingException {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new AccountNotExistingException("Account " + email + " not found");
        }
        return account.getBalance();
    }

}
