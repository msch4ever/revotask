package com.los.revotask.model.account;

import com.los.revotask.util.EventType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Ledger {

    private static long increment = 0;

    private final long ledgerId;
    private final long accountId;
    private final BigDecimal startBalance;
    private final BigDecimal endBalance;
    private final BigDecimal amount;
    private final Instant entryTime;
    private final String eventType;

    public Ledger(long accountId, BigDecimal startBalance, BigDecimal endBalance,
                  BigDecimal amount, EventType eventType) {
        this.ledgerId = increment;
        this.accountId = accountId;
        this.startBalance = startBalance;
        this.endBalance = endBalance;
        this.amount = amount;
        this.entryTime = Instant.now();
        this.eventType = eventType.getValue();
        increment++;
    }

    public long getLedgerId() {
        return ledgerId;
    }

    public long getAccountId() {
        return accountId;
    }

    public BigDecimal getStartBalance() {
        return startBalance;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public String getEventType() {
        return eventType;
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
                                          .format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss.SSS")) +
                ", eventType:'" + eventType + '\'' +
                '}';
    }
}
