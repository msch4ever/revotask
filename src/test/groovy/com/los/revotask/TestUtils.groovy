package com.los.revotask

import com.los.revotask.config.SessionFactoryProvider
import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import com.los.revotask.service.SessionUtils
import spark.utils.IOUtils

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
    
    static void saveUser(SessionUtils sessionUtils, UserDao dao, User givenUser) {
        sessionUtils.openAtomicTask()
        dao.save(givenUser)
        sessionUtils.commitAndCloseSession()
    }
    
    static User findUserById(SessionUtils sessionUtils, UserDao dao, Long id) {
        sessionUtils.openAtomicTask()
        User result = dao.findById(User.class, id)
        sessionUtils.commitAndCloseSession()
        result
    }
    
    static TestResponse request(String method, String path) {
        try {
            def url = new URL('http://localhost:4567' + path)
            def connection = url.openConnection() as HttpURLConnection
            connection.setRequestMethod(method)
            connection.setDoOutput(true)
            connection.connect()
            def body = IOUtils.toString(connection.inputStream)
            return new TestResponse(connection.responseCode, body)
        } catch (IOException e) {
            e.printStackTrace()
            return null
        }
    }
}
