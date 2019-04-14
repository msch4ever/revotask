package com.los.revotask.model.account;

import com.los.revotask.util.EventType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Entity
public class Ledger {

    private long ledgerId;
    private long accountId;
    private BigDecimal startBalance;
    private BigDecimal endBalance;
    private BigDecimal amount;
    private Instant entryTime;
    private String eventType;

    public Ledger() {
    }

    public Ledger(long accountId, BigDecimal startBalance, BigDecimal endBalance,
                  BigDecimal amount, EventType eventType) {
        this.accountId = accountId;
        this.startBalance = startBalance;
        this.endBalance = endBalance;
        this.amount = amount;
        this.eventType = eventType.getValue();
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(BigDecimal startBalance) {
        this.startBalance = startBalance;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    @CreationTimestamp
    public void setEntryTime(Instant entryTime) {
        this.entryTime = entryTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Ledger {" +
                "ledgerId:" + ledgerId +
                ", accountId:" + accountId +
                ", startBalance:" + startBalance +
                ", endBalance:" + endBalance +
                ", amount:" + amount +
                ", entryTime:" + entryTime.atZone(ZoneId.of("UTC"))
                                          .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")) +
                ", eventType:'" + eventType + '\'' +
                '}';
    }
}
