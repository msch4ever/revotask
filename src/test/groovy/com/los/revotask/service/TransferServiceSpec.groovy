package com.los.revotask.service

import com.los.revotask.model.account.Account
import com.los.revotask.model.account.Ledger
import com.los.revotask.model.user.User
import com.los.revotask.persistence.TransferDao
import com.los.revotask.transaction.Transfer
import spock.lang.Specification
import spock.lang.Unroll

import static com.los.revotask.TestUtils.decimal

class TransferServiceSpec extends Specification {
    
    private TransferService service
    
    void setup() {
        AccountService accountService = Mock()
        UserService userService = Mock()
        TransferDao transferDao = Mock()
        SessionUtils sessionUtils = Mock()
        
        service = new TransferService(accountService, userService, transferDao, sessionUtils)
    }
    
    void "Should save transfer"() {
        when:
            service.saveTransfer(new Transfer())
        then:
            1 * service.transferDao.save(_)
    }
    
    void "Should get All transfes"() {
        when:
            service.getAll()
        then:
            1 * service.transferDao.getAll(Transfer.class)
    }
    
    void "Should find all by accountId"() {
        when:
            service.findAllByAccountId(1L)
        then:
            1 * service.transferDao.findAllByAccountId(_ as Long)
    }
    
    void "Should transfer cash between two accounts"() {
        setup:
            Account sourceAccount = new Account(accountId: 1L, accountName: 'sourceAccount', balance: decimal(100))
            Account destinationAccount = new Account(accountId: 2L, accountName: 'destinationAccount', balance: decimal(100))
            
            User sourceUser = new User(userId: 1L, userName: 'sourceUser')
            sourceUser.setAccount(sourceAccount)
            User destinationUser = new User(userId: 2L, userName: 'destinationUser')
            destinationUser.setAccount(destinationAccount)
            BigDecimal amount = decimal(50)
        when:
            Transfer result = service.transferMoney(sourceUser.userId, destinationUser.userId, amount)
        then:
            result
            1 * service.userService.findById(1L) >> sourceUser
            1 * service.userService.findById(2L) >> destinationUser
            1 * service.accountService.isEnoughBalance(sourceAccount, amount) >> (amount == decimal(50))
            1 * service.accountService.updateAccount(sourceAccount, _ as Ledger)
            1 * service.accountService.updateAccount(destinationAccount, _ as Ledger)
            sourceAccount.getBalance() == decimal(50)
            destinationAccount.getBalance() == decimal(150)
    }
    
    @Unroll
    void "Should throw IAE during transfer"() {
        setup:
            sourceUser.setUserId(sourceUserId)
            destinationUser.setUserId(destinationUserId)
            BigDecimal amount = decimal(200)
        when:
            Transfer result = service.transferMoney(sourceUserId, destinationUserId, amount)
        then:
            !result
            thrown(IllegalArgumentException)
            service.userService.findById(sourceUserId) >> sourceUser
            service.userService.findById(destinationUserId) >> destinationUser
            expInvocations * service.accountService.isEnoughBalance(sourceUser?.account, amount) >> (amount == decimal(150))
            0 * service.accountService.updateAccount(_, _ as Ledger)
        where:
            sourceUserId | destinationUserId | sourceUser                                            | destinationUser                                                 | expInvocations
            1L           | 2L                | new User('sourceUser', 'sourceAccount', decimal(150)) | new User('destinationUser', 'destinationAccount', decimal(150)) | 1
            1L           | 1L                | new User('sameUser', 'account', decimal(150))         | new User('sameUser', 'account', decimal(150))                   | 0
    }
}
