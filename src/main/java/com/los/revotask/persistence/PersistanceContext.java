package com.los.revotask.persistence;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;

public class PersistanceContext {

    public final TransferDao transferDao;
    public final Dao<Account> accountDao;
    public final Dao<Ledger> ledgerDao;
    public final UserDao userDao;

    public PersistanceContext() {
        transferDao = new TransferDao();
        accountDao  = new DaoImpl<>();
        ledgerDao   = new DaoImpl<>();
        userDao     = new UserDao();
    }
}
