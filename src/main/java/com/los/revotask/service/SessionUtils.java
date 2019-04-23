package com.los.revotask.service;

import com.los.revotask.config.SessionFactoryProvider;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class SessionUtils {

    void openAtomicTask() {
        Transaction currentTransaction = getSessionFactory().getCurrentSession().getTransaction();
        if (currentTransaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
            currentTransaction.begin();
        }
    }

    void commitAndCloseSession() {
        getSessionFactory().getCurrentSession().getTransaction().commit();
        getSessionFactory().getCurrentSession().close();
    }

    private SessionFactory getSessionFactory() {
        return SessionFactoryProvider.getInstance().getSessionFactory();
    }
}
