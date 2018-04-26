package com.demo.wallet.controller;

import com.demo.wallet.controller.message.BaseResp;
import com.demo.wallet.controller.message.ErrorResp;
import com.demo.wallet.controller.message.RegisterReq;
import com.demo.wallet.controller.exception.AccountDuplicationException;
import com.demo.wallet.controller.message.RegisterResp;
import com.demo.wallet.repository.Account;
import com.demo.wallet.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public BaseResp registerAccount(@RequestBody RegisterReq req) {
        req.validate();
        try {
            Account account = accountService.registerNewAccount(req.getEmail());
            return new RegisterResp(account.getBalance().doubleValue());
        } catch (AccountDuplicationException e) {
            log.error("Failed to register new account", e);
            return new ErrorResp(e.getMessage());
        }
    }
}
