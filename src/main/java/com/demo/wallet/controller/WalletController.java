package com.demo.wallet.controller;

import com.demo.wallet.controller.exception.AccountNotExistingException;
import com.demo.wallet.controller.exception.BalanceInsufficientException;
import com.demo.wallet.controller.message.*;
import com.demo.wallet.repository.TransactionHistory;
import com.demo.wallet.service.AccountService;
import com.demo.wallet.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/wallet", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
public class WalletController {
    private static final Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public DeferredResult<BaseResp> checkBalance(@RequestBody CheckBalanceReq req) {
        req.validate();
        DeferredResult<BaseResp> deferredResult = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            try {
                BigDecimal balance = accountService.getBalance(req.getEmail());
                deferredResult.setResult(new CheckBalanceResp(balance.doubleValue()));
            } catch (AccountNotExistingException e) {
                log.error("Failed to check balance", e);
                deferredResult.setResult(new ErrorResp(e.getMessage()));
            }
        }, taskExecutor);
        return deferredResult;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public DeferredResult<BaseResp> createTransaction(@RequestBody TransferReq req) {
        req.validate();
        DeferredResult<BaseResp> deferredResult = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            try {
                transferService.processBalanceTransfer(req.getEmail(), req.getTransferee(), new BigDecimal(req.getAmount()).setScale(2, BigDecimal.ROUND_DOWN));
                log.info("Transaction successful: {}", req);
                deferredResult.setResult(new BaseResp(true));
            } catch (AccountNotExistingException e) {
                log.error("Failed to process transaction", e);
                deferredResult.setResult(new ErrorResp(e.getMessage()));
            } catch (BalanceInsufficientException e) {
                log.error("Failed to process transaction", e);
                deferredResult.setResult(new ErrorResp(e.getMessage()));
            }
        }, taskExecutor);
        return deferredResult;
    }

    @RequestMapping(value = "/transaction-history", method = RequestMethod.POST)
    public DeferredResult<BaseResp> getTransactionHistory(@RequestBody TransactionHistoryReq req) {
        req.validate();
        DeferredResult<BaseResp> deferredResult = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            try {
                List<TransactionHistory> historyList = transferService.getTransactionHistory(req.getEmail());
                List<Transaction> transactionList = historyList.stream().map(Transaction::fromTransactionHistory).collect(Collectors.toList());
                TransactionHistoryResp resp = new TransactionHistoryResp(transactionList);
                deferredResult.setResult(resp);
            } catch (AccountNotExistingException e) {
                log.error("Failed to get transaction history", e);
                deferredResult.setResult(new ErrorResp(e.getMessage()));
            }
        }, taskExecutor);
        return deferredResult;
    }
}
