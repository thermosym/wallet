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
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<BaseResp> registerAccount(@RequestBody RegisterReq req) {
        req.validate();
        DeferredResult<BaseResp> deferredResult = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            try {
                Account account = accountService.registerNewAccount(req.getEmail());
                deferredResult.setResult(new RegisterResp(account.getBalance().doubleValue()));
            } catch (AccountDuplicationException e) {
                log.error("Failed to register new account", e);
                deferredResult.setResult(new ErrorResp(e.getMessage()));
            }
        }, taskExecutor);
        return deferredResult;
    }
}
