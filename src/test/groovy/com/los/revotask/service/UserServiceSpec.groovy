package com.los.revotask.service

import static com.los.revotask.TestUtils.*

import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import spock.lang.Specification

class UserServiceSpec extends Specification {
    
    UserService service
    User user
    
    void setup() {
        user = new User(userId: 1L, userName: 'oldUserName')
        UserDao userDao = Mock() {
            findById(User.class, _ as Long) >> user
        }
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
        when:
            User resultUser = service.update(1L, 'newName')
        then:
            resultUser.getUserId() == 1L
            resultUser.getUserName() == 'newName'
    }
    
    void "Should not update user that does not exist"() {
        setup:
            UserDao dao = Mock() {
                findById(User.class, 99L) >> null
            }
            UserService userService = new UserService(dao)
        when:
            userService.update(99L, newName)
        then:
            thrown(IllegalArgumentException)
        where:
            newName << ['newName', '', null]
    }
    
    void "Should delete user"() {
        when:
            service.delete(user.userId)
        then:
            1 * service.userDao.delete(user)
    }
    
    void "Should not delete user that does not exist"() {
        setup:
            UserDao dao = Mock() {
                findById(User.class, 99L) >> null
            }
            UserService userService = new UserService(dao)
        when:
            userService.delete(99L)
        then:
            thrown(IllegalArgumentException)
    }
}
