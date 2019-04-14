package com.los.revotask.model.account;

import java.math.BigDecimal;

public class Account {

    private static long increment = 0;

    private long accountId;
    private String accountName;
    private BigDecimal balance;
    private long userId;

    public Account() {
    }

    public Account(String accountName, BigDecimal balance, long userId) {
        this.accountId = increment;
        this.accountName = accountName;
        this.balance = balance;
        this.userId = userId;
        increment++;
    }

    public Account(String accountName, long userId) {
        this.accountName = accountName;
        this.balance = BigDecimal.ZERO;
        this.userId = userId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return getAccountId() == account.getAccountId()
                && getUserId() == account.getUserId()
                && getAccountName().equals(account.getAccountName())
                && getBalance().equals(account.getBalance());
    }

    @Override
    public int hashCode() {
        int result = (int) (getAccountId() ^ (getAccountId() >>> 32));
        result = 31 * result + getAccountName().hashCode();
        result = 31 * result + getBalance().hashCode();
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Account{accountId: " + accountId + ", accountName: '" + accountName + "', balance: " + balance +'}';
    }
}
