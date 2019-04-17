package com.los.revotask

import com.los.revotask.config.SessionFactoryProvider

class TestUtils {
    
    static decimal(double amount) {
        return new BigDecimal(amount)
    }
    
    static cleanTables() {
        def sf = SessionFactoryProvider.getInstance().getSessionFactory()
        def session = sf.openSession()
        def querry = session.createSQLQuery('TRUNCATE TABLE USER; DELETE FROM ACCOUNT; TRUNCATE TABLE LEDGER; TRUNCATE TABLE TRANSFER')
        session.beginTransaction()
        querry.executeUpdate()
        session.getTransaction().commit()
        session.close()
    }
}
