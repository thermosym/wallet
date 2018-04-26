package com.demo.wallet.controller.message;

import java.util.List;

public class TransactionHistoryResp extends BaseResp {

    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public TransactionHistoryResp() {
        this.success = true;
    }

    public TransactionHistoryResp(List<Transaction> transactions) {
        this.success = true;
        this.transactions = transactions;
    }
}
