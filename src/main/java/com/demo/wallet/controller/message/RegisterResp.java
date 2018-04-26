package com.demo.wallet.controller.message;

public class RegisterResp extends BaseResp {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public RegisterResp() {
        this.success = true;
    }

    public RegisterResp(double balance) {
        this.success = true;
        this.balance = balance;
    }
}
