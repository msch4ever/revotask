package com.los.revotask.service

import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import spock.lang.Specification

import static com.los.revotask.TestUtils.decimal

class UserServiceSpec extends Specification {
    
    UserService service
    User user
    
    void setup() {
        user = new User(userId: 1L, userName: 'oldUserName')
        UserDao userDao = Mock()
        SessionUtils sessionUtils = Mock()
        service = new UserService(userDao, sessionUtils)
    }
    
    void "Should create user"() {
        when:
            service.createUser('userName', 'newAccount', decimal(1))
        then:
            1 * service.userDao.save(_)
    }
    
    void "Should find by Id"() {
        when:
            User result = service.findById(1L)
        then:
            1 * service.userDao.findById(User.class, _ as Long) >> user
            result
            result.userId == user.userId
            result.userName == user.userName
    }
    
    void "Should users by name"() {
        when:
            List<User> resultList = service.findByName("userName")
        then:
            1 * service.userDao.findByName("userName") >> [user]
            resultList.size() == 1
            resultList[0].userId == user.userId
            resultList[0].userName == user.userName
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
            1 * service.userDao.findById(User.class, user.userId) >> user
            resultUser.getUserId() == 1L
            resultUser.getUserName() == 'newName'
    }
    
    void "Should not update user that does not exist"() {
        when:
            service.update(99L, newName)
        then:
            0 * service.userDao.update(_)
            thrown(IllegalArgumentException)
        where:
            newName << ['newName', '', null]
    }
    
    void "Should delete user"() {
        when:
            service.delete(user.userId)
        then:
            1 * service.userDao.findById(User.class, user.userId) >> user
            1 * service.userDao.delete(user)
    }
    
    void "Should not delete user that does not exist"() {
        when:
            service.delete(99L)
        then:
            1 * service.userDao.findById(_, _) >> null
            0 * service.userDao.delete(_)
            thrown(IllegalArgumentException)
    }
}
