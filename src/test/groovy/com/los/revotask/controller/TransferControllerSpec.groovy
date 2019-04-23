package com.los.revotask.controller

import com.google.gson.Gson
import com.los.revotask.app.ApplicationServer
import com.los.revotask.TestResponse
import com.los.revotask.model.user.User
import com.los.revotask.persistence.UserDao
import com.los.revotask.service.SessionUtils
import com.los.revotask.transaction.Transfer
import spark.Spark
import spock.lang.Specification

import static com.los.revotask.TestUtils.*

class TransferControllerSpec extends Specification {
    
    private SessionUtils sessionUtils
    
    void setup() {
        ApplicationServer.startServer()
        sessionUtils = new SessionUtils()
    }
    
    void cleanup() {
        Spark.stop()
        cleanTables()
    }
    
    void "Should transfer money across the accounts"() {
        setup:
            User sourceUser = new User('sourceUser', 'source', decimal(100))
            User destinationUser = new User('destinationUser', 'destination', decimal(100))
            UserDao dao = new UserDao()
            saveUser(sessionUtils, dao, sourceUser)
            saveUser(sessionUtils, dao, destinationUser)
        when:
            TestResponse res = request('PUT', '/transfer?sourceUserId=' + sourceUser.userId
                    + '&destinationUserId=' + destinationUser.userId + '&amount=100')
        then:
            res.status == 200
            Transfer result = new Gson().fromJson(res.body, Transfer.class)
            result.sourceAccountId == sourceUser.account.accountId
            result.sourceStartBalance == sourceUser.account.balance
            result.sourceResultBalance == 0
            result.destinationAccountId == destinationUser.account.accountId
            result.destinationStartBalance == destinationUser.account.balance
            result.destinationResultBalance == 200
            result.amount == 100
    }
    
    void "Should rollback transfer if not enough money on the source"() {
        setup:
            User sourceUser = new User('sourceUser', 'source', decimal(100))
            User destinationUser = new User('destinationUser', 'destination', decimal(100))
            UserDao dao = new UserDao()
            saveUser(sessionUtils, dao, sourceUser)
            saveUser(sessionUtils, dao, destinationUser)
        when:
            TestResponse res = request('PUT', '/transfer?sourceUserId=' + sourceUser.userId
                    + '&destinationUserId=' + destinationUser.userId + '&amount=150')
        then:
            !res
    }
    
    void "Should rollback transfer if source and destination are tha same"() {
        setup:
            User sameUser = new User('sameUser', 'sameAccount', decimal(100))
            UserDao dao = new UserDao()
            saveUser(sessionUtils, dao, sameUser)
        when:
            TestResponse res = request('PUT', '/transfer?sourceUserId=' + sameUser.userId
                    + '&destinationUserId=' + sameUser.userId + '&amount=150')
        then:
            !res
    }
    
    void "Should rollback transfer if user does not exist"() {
        when:
            TestResponse res = request('PUT', '/transfer?sourceUserId=98&destinationUserId=99&amount=100')
        then:
            !res
    }
}
