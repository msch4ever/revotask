package com.los.revotask.model.account;

import com.los.revotask.model.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Account {

    private long accountId;
    private String accountName;
    private BigDecimal balance;

    public Account() {
    }

    public Account(String accountName, BigDecimal balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public Account(String accountName) {
        this.accountName = accountName;
        this.balance = BigDecimal.ZERO;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
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
                && getAccountName().equals(account.getAccountName())
                && getBalance().equals(account.getBalance());
    }

    @Override
    public int hashCode() {
        int result = (int) (getAccountId() ^ (getAccountId() >>> 32));
        result = 31 * result + getAccountName().hashCode();
        result = 31 * result + (getBalance() != null ? getBalance().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account {accountId: " + accountId + ", accountName: '" + accountName + "', balance: " + balance +'}';
    }
}
