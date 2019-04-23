package com.los.revotask.service;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import com.los.revotask.persistence.Dao;
import com.los.revotask.persistence.PersistenceContext;
import com.los.revotask.transaction.EventType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Transactional(Transactional.TxType.SUPPORTS)
public class AccountService extends AbstractService {

    public AccountService(final PersistenceContext persistenceContext) {
        super(persistenceContext);
    }

    public long createAccount(String accountName, BigDecimal balance) {
        openAtomicTask();
        long id = getAccountDao().save(new Account(accountName, balance));
        commitAndCloseSession();
        return id;
    }

    public long createAccount(String accountName) {
        return createAccount(accountName, new BigDecimal(0.0));
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void updateAccount(Account account, Ledger ledger) {
        getAccountDao().update(account);
        getLedgerDao().save(ledger);
    }

    public List<Account> getAll() {
        openAtomicTask();
        List<Account> resultList = getAccountDao().getAll(Account.class);
        commitAndCloseSession();
        return resultList;
    }

    public boolean isEnoughBalance(Account account, BigDecimal amount) {
        return (account.getBalance() != null && amount != null) && account.getBalance().compareTo(amount) >= 0;
    }

    public Ledger topUp(Account account, BigDecimal amount) {
        openAtomicTask();
        BigDecimal startBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().add(amount);
        account.setBalance(resultBalance);
        return commitOperation(account, startBalance, resultBalance, amount, EventType.TOP_UP);
    }

    public Ledger withdraw(Account account, BigDecimal amount) {
        openAtomicTask();
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
        commitAndCloseSession();
        return ledger;
    }
}
