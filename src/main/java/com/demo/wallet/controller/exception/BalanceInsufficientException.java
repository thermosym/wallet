package com.demo.wallet.controller.exception;

public class BalanceInsufficientException extends Exception {
    public BalanceInsufficientException(String msg) {
        super(msg);
    }
}
