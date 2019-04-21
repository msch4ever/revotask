package com.los.revotask.controller

import com.google.gson.Gson
import com.los.revotask.Application
import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import spark.Spark
import spark.utils.IOUtils
import spock.lang.Specification

import static com.los.revotask.TestUtils.*

class UserControllerSpec extends Specification {
    
    void setup() {
        Application.main(null)
    }
    
    void cleanup() {
        Spark.stop()
        cleanTables()
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
            dao.save(givenUser)
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
            dao.save(new User(userName: 'testUser1'))
            dao.save(new User(userName: 'testUser2'))
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
            dao.save(givenUser)
        when:
            TestResponse res = request('GET', '/users/' + givenUser.userId)
        then:
            Map<String, String> json = res.json()
            res.status == 200
            json.userName == 'testUser'
            json.userId == givenUser.userId
    }
    
    private static TestResponse request(String method, String path) {
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
    
    private static class TestResponse {
        
        final String body
        final int status
        
        TestResponse(int status, String body) {
            this.status = status
            this.body = body
        }
        
        Map<String, String> json() {
            return new Gson().fromJson(body, HashMap.class)
        }
    }
}
