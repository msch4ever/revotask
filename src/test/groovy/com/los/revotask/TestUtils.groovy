package com.los.revotask

import com.los.revotask.config.SessionFactoryProvider

class TestUtils {
    
    static decimal(double amount) {
        return new BigDecimal(amount)
    }
    
    static cleanTables() {
        def sf = SessionFactoryProvider.getInstance().getSessionFactory()
        def session = sf.openSession()
        def querry = session.createSQLQuery(
                'ALTER TABLE USER DROP CONSTRAINT ACCOUNT_FK;' +
                        'DELETE FROM USER;' +
                        'DELETE FROM ACCOUNT;' +
                        'DELETE FROM LEDGER;' +
                        'DELETE FROM TRANSFER;' +
                        'ALTER TABLE USER ADD CONSTRAINT ACCOUNT_FK FOREIGN KEY (ACCOUNTID) REFERENCES ACCOUNT (ACCOUNTID);')
        session.beginTransaction()
        querry.executeUpdate()
        session.getTransaction().commit()
        session.close()
    }
}
