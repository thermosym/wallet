package com.demo.wallet.controller.message;

import com.demo.wallet.controller.exception.RequestValidationException;
import org.apache.commons.lang3.StringUtils;

public class TransferReq extends BaseReq {
    private String transferee;
    private double amount;

    @Override
    public void validate() {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(transferee)) {
            throw new RequestValidationException("Email/Transferee should not be blank");
        }

        if (email.equals(transferee)) {
            throw new RequestValidationException("You cannot transfer to yourself");
        }

        if (amount <= 0) {
            throw new RequestValidationException("The transfer amount should larger than 0");
        }
    }

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
