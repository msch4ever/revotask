package com.los.revotask.transaction;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Entity
public class Transfer {

    private long transferId;
    private long sourceAccountId;
    private long destinationAccountId;
    private BigDecimal amount;
    private BigDecimal sourceStartBalance;
    private BigDecimal destinationStartBalance;
    private BigDecimal sourceResultBalance;
    private BigDecimal destinationResultBalance;
    private Instant entryTime;

    public Transfer() {
    }

    public Transfer(long sourceAccountId, long destinationAccountId, TransferInfo info) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = info.getAmount();
        this.sourceStartBalance = info.getSourceStartBalance();
        this.destinationStartBalance = info.getDestinationStartBalance();
        this.sourceResultBalance = info.getSourceResultBalance();
        this.destinationResultBalance = info.getDestinationResultBalance();
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSourceStartBalance() {
        return sourceStartBalance;
    }

    public void setSourceStartBalance(BigDecimal sourceStartBalance) {
        this.sourceStartBalance = sourceStartBalance;
    }

    public BigDecimal getDestinationStartBalance() {
        return destinationStartBalance;
    }

    public void setDestinationStartBalance(BigDecimal destinationStartBalance) {
        this.destinationStartBalance = destinationStartBalance;
    }

    public BigDecimal getSourceResultBalance() {
        return sourceResultBalance;
    }

    public void setSourceResultBalance(BigDecimal sourceResultBalance) {
        this.sourceResultBalance = sourceResultBalance;
    }

    public BigDecimal getDestinationResultBalance() {
        return destinationResultBalance;
    }

    public void setDestinationResultBalance(BigDecimal destinationResultBalance) {
        this.destinationResultBalance = destinationResultBalance;
    }

    @CreationTimestamp
    public Instant getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Instant entryTime) {
        this.entryTime = entryTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfer)) return false;

        Transfer transfer = (Transfer) o;

        if (getTransferId() != transfer.getTransferId()) {
            return false;
        }
        if (getSourceAccountId() != transfer.getSourceAccountId()) {
            return false;
        }
        if (getDestinationAccountId() != transfer.getDestinationAccountId()) {
            return false;
        }
        if (getAmount().compareTo((transfer.getAmount())) != 0) {
            return false;
        }
        if (getSourceStartBalance().compareTo((transfer.getSourceStartBalance())) != 0) {
            return false;
        }
        if (getDestinationStartBalance().compareTo((transfer.getDestinationStartBalance())) != 0) {
            return false;
        }
        if (getSourceResultBalance().compareTo((transfer.getSourceResultBalance())) != 0) {
            return false;
        }
        if (getDestinationResultBalance().compareTo((transfer.getDestinationResultBalance())) != 0) {
            return false;
        }
        return getEntryTime().equals(transfer.getEntryTime());
    }

    @Override
    public int hashCode() {
        int result = (int) (getTransferId() ^ (getTransferId() >>> 32));
        result = 31 * result + (int) (getSourceAccountId() ^ (getSourceAccountId() >>> 32));
        result = 31 * result + (int) (getDestinationAccountId() ^ (getDestinationAccountId() >>> 32));
        result = 31 * result + getAmount().hashCode();
        result = 31 * result + getSourceStartBalance().hashCode();
        result = 31 * result + getDestinationStartBalance().hashCode();
        result = 31 * result + getSourceResultBalance().hashCode();
        result = 31 * result + getDestinationResultBalance().hashCode();
        result = 31 * result + getEntryTime().hashCode();
        return result;
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
                                          .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) +
                '}';
    }
}
