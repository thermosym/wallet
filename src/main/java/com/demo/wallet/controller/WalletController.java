package com.demo.wallet.controller;

import com.demo.wallet.controller.exception.AccountNotFoundException;
import com.demo.wallet.controller.message.*;
import com.demo.wallet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/wallet", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
public class WalletController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public BaseResp checkBalance(@RequestBody CheckBalanceReq req) {
        try {
            BigDecimal balance = accountService.getBalance(req.getEmail());
            return new CheckBalanceResp(balance.doubleValue());
        } catch (AccountNotFoundException e) {
            return new ErrorResp(e.getMessage());
        }
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public BaseResp createTransaction(@RequestBody TransferReq req) {
        return new ErrorResp("No account");
    }

    @RequestMapping(value = "/transaction-history", method = RequestMethod.POST)
    public BaseResp getTransactionHistory(@RequestBody TransactionHistoryReq req) {
        return null;
    }
}
