package com.los.revotask.transaction;

import com.los.revotask.model.account.Account;

import java.math.BigDecimal;

public class TransferInfo {
    private final BigDecimal amount;
    private final BigDecimal sourceStartBalance;
    private final BigDecimal destinationStartBalance;
    private final BigDecimal sourceResultBalance;
    private final BigDecimal destinationResultBalance;

    public TransferInfo(Account source, Account destination, BigDecimal amount) {
        this.amount = amount;
        this.sourceStartBalance = source.getBalance();
        this.sourceResultBalance = source.getBalance().subtract(this.amount);
        this.destinationStartBalance = destination.getBalance();
        this.destinationResultBalance = destination.getBalance().add(this.amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getSourceStartBalance() {
        return sourceStartBalance;
    }

    public BigDecimal getDestinationStartBalance() {
        return destinationStartBalance;
    }

    public BigDecimal getSourceResultBalance() {
        return sourceResultBalance;
    }

    public BigDecimal getDestinationResultBalance() {
        return destinationResultBalance;
    }
}