package com.demo.wallet.controller.exception;

public class AccountNotExistingException extends Exception {

    public AccountNotExistingException(String message) {
        super(message);
    }

}
