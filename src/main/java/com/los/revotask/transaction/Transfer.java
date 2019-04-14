package com.los.revotask.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Transfer {

    private static long increment = 0;

    private final long transferId;
    private final long sourceAccountId;
    private final long destinationAccountId;
    private final BigDecimal amount;
    private final BigDecimal sourceStartBalance;
    private final BigDecimal destinationStartBalance;
    private final BigDecimal sourceResultBalance;
    private final BigDecimal destinationResultBalance;
    private final Instant entryTime;

    public Transfer(long sourceAccountId, long destinationAccountId, TransferInfo info) {
        this.transferId = increment;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = info.getAmount();
        this.sourceStartBalance = info.getSourceStartBalance();
        this.destinationStartBalance = info.getDestinationStartBalance();
        this.sourceResultBalance = info.getSourceResultBalance();
        this.destinationResultBalance = info.getDestinationResultBalance();
        this.entryTime = Instant.now();
        increment++;
    }

    public long getTransferId() {
        return transferId;
    }

    public long getSourceAccountId() {
        return sourceAccountId;
    }

    public long getDestinationAccountId() {
        return destinationAccountId;
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

    public Instant getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return "Transfer {" +
                "transferId:" + transferId +
                ", sourceAccountId:" + sourceAccountId +
                ", destinationAccountId:" + destinationAccountId +
                ", amount:" + amount +
                ", sourceStartBalance:" + sourceStartBalance +
                ", destinationStartBalance:" + destinationStartBalance +
                ", sourceResultBalance:" + sourceResultBalance +
                ", destinationResultBalance:" + destinationResultBalance +
                ", entryTime:" + entryTime.atZone(ZoneId.of("UTC"))
                                          .format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss.SSS")) +
                '}';
    }
}
