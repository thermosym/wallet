package com.demo.wallet.controller.message;

public class TransferReq extends BaseReq {
    private String transferee;
    private double amount;

    public String getTransferee() {
        return transferee;
    }

    public void setTransferee(String transferee) {
        this.transferee = transferee;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
