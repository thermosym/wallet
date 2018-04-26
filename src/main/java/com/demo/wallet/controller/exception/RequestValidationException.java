package com.demo.wallet.controller.exception;

public class RequestValidationException extends IllegalArgumentException {

    public RequestValidationException() {
    }

    public RequestValidationException(String msg) {
        super(msg);
    }
}
