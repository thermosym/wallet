package com.demo.wallet.controller.message;


import com.demo.wallet.repository.TransactionHistory;
import com.demo.wallet.util.TimeUtil;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.ZoneId;

public class Transaction {
    private Long id;
    private String from;
    private String to;
    private String type;
    private Double amount;
    private String datetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public static Transaction fromTransactionHistory(TransactionHistory history) {
        Transaction transaction = new Transaction();
        transaction.id = history.getId();
        transaction.from = history.getDebitAccount();
        transaction.to = history.getCreditAccount();
        transaction.type = history.getTransactionType();
        transaction.amount = history.getAmount().doubleValue();
        transaction.datetime = TimeUtil.dbTsToRFC3339(history.getExecutionTime(), ZoneId.of("GMT+8"));
        return transaction;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
