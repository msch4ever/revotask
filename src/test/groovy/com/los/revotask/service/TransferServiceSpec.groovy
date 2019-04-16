package com.los.revotask.service

import com.los.revotask.model.account.Account
import com.los.revotask.model.account.Ledger
import com.los.revotask.model.user.User
import com.los.revotask.persistence.TransferDao
import com.los.revotask.transaction.Transfer
import spock.lang.Specification

class TransferServiceSpec extends Specification {
    
    private TransferService service
    
    void setup() {
        AccountService accountService = Mock()
        UserService userService = Mock()
        TransferDao transferDao = Mock()
        
        service = new TransferService(accountService, userService, transferDao)
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
            BigDecimal amount = transferAmount
        when:
            Optional<Transfer> result = service.transferMoney(sourceUser.userId, destinationUser.userId, amount)
        then:
            result.isPresent() == expPresent
            1 * service.userService.findById(1L) >> sourceUser
            1 * service.userService.findById(2L) >> destinationUser
            1 * service.accountService.isEnoughBalance(sourceAccount, amount) >> (amount == decimal(50))
            expInvokations * service.accountService.updateAccount(sourceAccount, _ as Ledger)
            expInvokations * service.accountService.updateAccount(destinationAccount, _ as Ledger)
            
            sourceAccount.getBalance() == expSourceResult
            destinationAccount.getBalance() == expDestinationResult
        
        where:
            transferAmount | expSourceResult | expDestinationResult | expPresent | expInvokations
            decimal(50)    | decimal(50)     | decimal(150)         | true       | 1
            decimal(150)   | decimal(100)    | decimal(100)         | false      | 0
        
    }
    
    private static decimal(double amount) {
        return new BigDecimal(amount)
    }
}
