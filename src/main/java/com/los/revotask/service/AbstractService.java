package com.los.revotask.service;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import com.los.revotask.persistence.Dao;
import com.los.revotask.persistence.PersistenceContext;
import com.los.revotask.persistence.TransferDao;
import com.los.revotask.persistence.UserDao;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public abstract class AbstractService {

    private final PersistenceContext persistenceContext;

    public AbstractService(final PersistenceContext persistenceContext) {
        this.persistenceContext = persistenceContext;
    }

    void openAtomicTask() {
        Transaction currentTransaction = persistenceContext.sessionFactory.getCurrentSession().getTransaction();
        if (currentTransaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
            currentTransaction.begin();
        }
    }

    void commitAndCloseSession() {
        persistenceContext.sessionFactory.getCurrentSession().getTransaction().commit();
        persistenceContext.sessionFactory.getCurrentSession().close();
    }

    UserDao getUserDao() {
        return persistenceContext.userDao;
    }

    Dao<Account> getAccountDao() {
        return persistenceContext.accountDao;
    }

    Dao<Ledger> getLedgerDao() {
        return persistenceContext.ledgerDao;
    }

    TransferDao getTransferDao() {
        return persistenceContext.transferDao;
    }
}
