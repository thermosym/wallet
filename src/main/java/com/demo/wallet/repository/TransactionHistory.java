package com.demo.wallet.repository;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "debit_account")
    private String debitAccount;

    @Column(name = "credit_account")
    private String creditAccount;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_amount", precision = 16, scale = 2)
    private BigDecimal amount;

    @Column(name = "execution_time")
    private Timestamp executionTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "debit_status")
    private TransactionStatus debitStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_status")
    private TransactionStatus creditStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Timestamp executionTime) {
        this.executionTime = executionTime;
    }

    public TransactionStatus getDebitStatus() {
        return debitStatus;
    }

    public void setDebitStatus(TransactionStatus debitStatus) {
        this.debitStatus = debitStatus;
    }

    public TransactionStatus getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(TransactionStatus creditStatus) {
        this.creditStatus = creditStatus;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
