package com.los.revotask.service;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import com.los.revotask.persistence.Dao;
import com.los.revotask.transaction.EventType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Transactional
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

    @Transactional(Transactional.TxType.SUPPORTS)
    public void updateAccount(Account account, Ledger ledger) {
        accountDao.update(account);
        ledgerDao.save(ledger);
    }

    public List<Account> getAll() {
        return accountDao.getAll(Account.class);
    }

    public boolean isEnoughBalance(Account account, BigDecimal amount) {
        return (account.getBalance() != null && amount != null) && account.getBalance().compareTo(amount) >= 0;
    }

    public Ledger topUp(Account account, BigDecimal amount) {
        BigDecimal startBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().add(amount);
        account.setBalance(resultBalance);
        return commitOperation(account, startBalance, resultBalance, amount, EventType.TOP_UP);
    }

    public Optional<Ledger> withdraw(Account account, BigDecimal amount) {
        if (!isEnoughBalance(account, amount)) {
            return Optional.empty();
        }
        BigDecimal startBalance = account.getBalance();
        BigDecimal resultBalance = account.getBalance().subtract(amount);
        account.setBalance(resultBalance);
        return Optional.of(commitOperation(account, startBalance, resultBalance, amount, EventType.WITHDRAW));
    }

    private Ledger commitOperation(Account account, BigDecimal startBalance, BigDecimal endBalance,
                                   BigDecimal amount, EventType type) {
        Ledger ledger = new Ledger(account.getAccountId(), startBalance, endBalance, amount, type);
        updateAccount(account, ledger);
        return ledger;
    }
}
