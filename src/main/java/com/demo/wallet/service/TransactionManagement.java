package com.demo.wallet.service;

import com.demo.wallet.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
public class TransactionManagement {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(rollbackOn = Throwable.class)
    public boolean deductBalance(String email, BigDecimal amount) {
        int numberOfRowAffected = accountRepository.deductBalance(email, amount);
        if (numberOfRowAffected > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean addBalance(String email, BigDecimal amount) {
        int numberOfRowAffected = accountRepository.addBalance(email, amount);
        if (numberOfRowAffected > 0) {
            return true;
        } else {
            return false;
        }
    }

}
