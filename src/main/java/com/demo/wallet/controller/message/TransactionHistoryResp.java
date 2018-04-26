package com.demo.wallet.controller.message;


import com.demo.wallet.repository.TransactionHistory;

import java.util.List;

public class TransactionHistoryResp extends BaseResp {

    private List<TransactionHistory> transactions;

    public List<TransactionHistory> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionHistory> transactions) {
        this.transactions = transactions;
    }

    public TransactionHistoryResp() {
        this.success = true;
    }

    public TransactionHistoryResp(List<TransactionHistory> transactions) {
        this.success = true;
        this.transactions = transactions;
    }
}
