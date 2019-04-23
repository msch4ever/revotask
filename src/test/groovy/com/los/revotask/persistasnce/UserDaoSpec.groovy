package com.los.revotask.persistasnce

import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import com.los.revotask.service.SessionUtils
import spock.lang.Specification

import static com.los.revotask.TestUtils.cleanTables
import static com.los.revotask.TestUtils.decimal

class UserDaoSpec extends Specification {
    
    private SessionUtils sessionUtils
    private UserDao dao
    
    void setup() {
        sessionUtils = new SessionUtils()
        sessionUtils.openAtomicTask()
        dao = new UserDao()
    }
    
    void cleanup() {
        sessionUtils.commitAndCloseSession()
        cleanTables()
    }
    
    void 'Should find User by name'() {
        setup:
            User givenUser1 = new User('userName', 'account', decimal(100))
            User givenUser2 = new User('userName', 'account', decimal(100))
            dao.save(givenUser1)
            dao.save(givenUser2)
        when:
            List<User> resultList = dao.findByName('userName')
        then:
            resultList.size() == 2
            resultList.containsAll([givenUser1, givenUser2])
    }
}
