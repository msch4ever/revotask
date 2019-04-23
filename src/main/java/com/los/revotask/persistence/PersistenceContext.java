package com.los.revotask.persistence;

import com.los.revotask.config.SessionFactoryProvider;
import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import org.hibernate.SessionFactory;

public class PersistenceContext {

    public final SessionFactory sessionFactory;
    public final TransferDao transferDao;
    public final Dao<Account> accountDao;
    public final Dao<Ledger> ledgerDao;
    public final UserDao userDao;

    public PersistenceContext() {
        this.sessionFactory = SessionFactoryProvider.getInstance().getSessionFactory();
        transferDao = new TransferDao();
        accountDao  = new DaoImpl<>();
        ledgerDao   = new DaoImpl<>();
        userDao     = new UserDao();
    }
}
