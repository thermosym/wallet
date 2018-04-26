package com.demo.wallet.controller.message;

public class CheckBalanceResp extends BaseResp {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CheckBalanceResp() {
        this.success = true;
    }

    public CheckBalanceResp(double balance) {
        this.success = true;
        this.balance = balance;
    }
}
