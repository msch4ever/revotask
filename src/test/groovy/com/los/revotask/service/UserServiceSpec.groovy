package com.los.revotask.service

import static com.los.revotask.TestUtils.*

import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import spock.lang.Specification

class UserServiceSpec extends Specification {
    
    UserService service
    
    void setup() {
        UserDao userDao = Mock()
        service = new UserService(userDao)
    }
    
    void "Should create user"() {
        when:
            service.createUser('userName', 'newAccount', decimal(1))
        then:
            1 * service.userDao.save(_)
    }
    
    void "Should find by Id"() {
        when:
            service.findById(1L)
        then:
            1 * service.userDao.findById(User.class, _ as Long)
    }
    
    void "Should users by name"() {
        when:
            service.findByName("userName")
        then:
            1 * service.userDao.findByName("userName")
    }
    
    void "Should get All users"() {
        when:
            service.getAll()
        then:
            1 * service.userDao.getAll(User.class)
    }
    
    void "Should update user"() {
        setup:
            User user = new User()
        when:
            service.update(user)
        then:
            1 * service.userDao.update(user)
    }
    
    void "Should delete user"() {
        setup:
            User user = new User()
        when:
            service.delete(user)
        then:
            1 * service.userDao.delete(user)
    }
}
