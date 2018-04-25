package com.demo.wallet.controller;

import com.demo.wallet.controller.message.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/wallet", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
public class WalletController {

    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public BaseResp checkBalance(@RequestBody CheckBalanceReq req) {
        return new CheckBalanceResp(0);
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
