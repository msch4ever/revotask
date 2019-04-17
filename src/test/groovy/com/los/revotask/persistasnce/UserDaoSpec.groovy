package com.los.revotask.persistasnce

import static com.los.revotask.TestUtils.*

import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import spock.lang.Specification

class UserDaoSpec extends Specification {
    
    UserDao dao
    
    void setup() {
        cleanTables()
        dao = new UserDao()
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
