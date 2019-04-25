package com.los.revotask.controller

import com.los.revotask.app.ApplicationServer
import com.los.revotask.TestResponse
import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import com.los.revotask.service.SessionUtils
import org.junit.AfterClass
import org.junit.BeforeClass
import spark.Spark
import spock.lang.Specification

import static com.los.revotask.TestUtils.*

class UserControllerSpec extends Specification {
    
    private SessionUtils sessionUtils

    @BeforeClass
    static void beforeClass() {
        ApplicationServer.startServer()
    }

    void setup() {
        sessionUtils = new SessionUtils()
    }
    
    void cleanup() {
        cleanTables()
    }

    @AfterClass
    static void afterClass() {
        Spark.stop()
    }

    void 'Should create new User'() {
        when:
            TestResponse res = request('POST', '/users?userName=Bill&accountName=main&accountAmount=100.50')
        then:
            Map<String, String> json = res.json()
            res.status == 200
            json.userName == 'Bill'
            json.account
            json.userId
    }
    
    void 'Should update existing User'() {
        setup:
            User givenUser = new User(userName: 'testUser')
            UserDao dao = new UserDao()
            saveUser(sessionUtils, dao, givenUser)
        when:
            TestResponse res = request('PUT', '/users/' + givenUser.userId + '?newUserName=newName')
        then:
            Map<String, String> json = res.json()
            res.status == 200
            json.userName == 'newName'
    }
    
    void 'Should get all existing Users'() {
        setup:
            UserDao dao = new UserDao()
            saveUser(sessionUtils, dao, new User(userName: 'testUser1'))
            saveUser(sessionUtils, dao, new User(userName: 'testUser2'))
        when:
            TestResponse res = request('GET', '/users')
        then:
            res.status == 200
            res.body.contains('testUser1')
            res.body.contains('testUser2')
    }
    
    void 'Should find existing User by id'() {
        setup:
            User givenUser = new User(userName: 'testUser')
            UserDao dao = new UserDao()
            saveUser(sessionUtils, dao, givenUser)
        when:
            TestResponse res = request('GET', '/users/' + givenUser.userId)
        then:
            Map<String, String> json = res.json()
            res.status == 200
            json.userName == 'testUser'
            json.userId == givenUser.userId
    }
    
    void 'Should delete existing User by id'() {
        setup:
            User givenUser = new User(userName: 'testUser')
            UserDao dao = new UserDao()
            saveUser(sessionUtils, dao, givenUser)
        when:
            request('PUT', '/users/delete/' + givenUser.userId)
        then:
            !findUserById(sessionUtils, dao, givenUser.userId)
    }
}
