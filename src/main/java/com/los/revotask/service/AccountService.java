package com.los.revotask.service;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import com.los.revotask.persistence.Dao;
import com.los.revotask.transaction.EventType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional(Transactional.TxType.SUPPORTS)
public class AccountService {

    private final Dao<Account> accountDao;
    private final Dao<Ledger> ledgerDao;
    private final SessionUtils SessionUtils;

    public AccountService(Dao<Account> accountDao, Dao<Ledger> ledgerDao, SessionUtils SessionUtils) {
        this.accountDao = accountDao;
        this.ledgerDao = ledgerDao;
        this.SessionUtils = SessionUtils;
    }

    public long createAccount(String accountName, BigDecimal balance) {
        SessionUtils.openAtomicTask();
        long id = accountDao.save(new Account(accountName, balance));
        SessionUtils.commitAndCloseSession();
        return id;
    }

    public long createAccount(String accountName) {
        return createAccount(accountName, new BigDecimal(0.0));
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void updateAccount(Account account, Ledger ledger) {
        accountDao.update(account);
        ledgerDao.save(ledger);
    }

    public List<Account> getAll() {
        SessionUtils.openAtomicTask();
        List<Account> resultList = accountDao.getAll(Account.class);
        SessionUtils.commitAndCloseSession();
        return resultList;
    }

    public boolean isEnoughBalance(Account account, BigDecimal amount) {
        return (account.getBalance() != null && amount != null) && account.getBalance().compareTo(amount) >= 0;
    }

    public Ledger topUp(Account account, BigDecimal amount) {
        SessionUtils.openAtomicTask();
        BigDecimal startBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().add(amount);
        account.setBalance(resultBalance);
        return commitOperation(account, startBalance, resultBalance, amount, EventType.TOP_UP);
    }

    public Ledger withdraw(Account account, BigDecimal amount) {
        SessionUtils.openAtomicTask();
        if (!isEnoughBalance(account, amount)) {
            throw new IllegalArgumentException("Not enough balance for operation.");
        }
        BigDecimal startBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().subtract(amount);
        account.setBalance(resultBalance);
        return commitOperation(account, startBalance, resultBalance, amount, EventType.WITHDRAW);
    }

    private Ledger commitOperation(Account account, BigDecimal startBalance, BigDecimal endBalance,
                                   BigDecimal amount, EventType type) {
        Ledger ledger = new Ledger(account.getAccountId(), startBalance, endBalance, amount, type);
        updateAccount(account, ledger);
        SessionUtils.commitAndCloseSession();
        return ledger;
    }
}
