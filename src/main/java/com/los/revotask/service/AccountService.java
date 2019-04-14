package com.los.revotask.service;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import com.los.revotask.persistence.Dao;
import com.los.revotask.util.EventType;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    private final Dao<Account> accountDao;
    private final Dao<Ledger> ledgerDao;

    public AccountService(Dao<Account> accountDao, Dao<Ledger> ledgerDao) {
        this.accountDao = accountDao;
        this.ledgerDao = ledgerDao;
    }

    public long createAccount(String accountName, BigDecimal balance) {
        return accountDao.save(new Account(accountName, balance));
    }

    public void createAccount(String accountName) {
        createAccount(accountName, new BigDecimal(0.0));
    }

    public void updateAccount(Account account, Ledger ledger) {
        accountDao.update(account);
        ledgerDao.save(ledger);
    }

    public List<Account> getAll() {
        return accountDao.getAll(Account.class);
    }

    public boolean isEnoughBalance(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) >= 0;
    }

    public  boolean topUp(Account account, BigDecimal amount) {
        BigDecimal startBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().add(amount);

        account.setBalance(resultBalance);

        if (account.getBalance().subtract(amount).equals(startBalance)) {
            return commitTopUp(account.getAccountId(), startBalance, resultBalance, amount);
        } else {
            account.setBalance(startBalance);
            return false;
        }
    }

    public  boolean withdraw(Account account, BigDecimal amount) {
        if (!isEnoughBalance(account, amount)) {
            return false;
        }
        BigDecimal startBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().subtract(amount);

        account.setBalance(resultBalance);

        if (account.getBalance().add(amount).equals(startBalance)) {
            return commitWithdraw(account.getAccountId(), startBalance, resultBalance, amount);
        } else {
            account.setBalance(startBalance);
            return false;
        }
    }

    //could be just commit
    private boolean commitTopUp(long accountId, BigDecimal startBalance, BigDecimal endBalance,BigDecimal amount) {
        //SAVE TO DB
        System.out.println(new Ledger(accountId, startBalance, endBalance, amount, EventType.TOP_UP));
        return true;
    }

    private boolean commitWithdraw(long accountId, BigDecimal startBalance, BigDecimal endBalance,BigDecimal amount) {
        //SAVE TO DB
        System.out.println(new Ledger(accountId, startBalance, endBalance, amount, EventType.WITHDRAW));
        return true;
    }
}
