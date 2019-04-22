package com.los.revotask.service

import static com.los.revotask.TestUtils.*

import com.los.revotask.model.account.Account
import com.los.revotask.model.account.Ledger
import com.los.revotask.persistence.Dao
import com.los.revotask.transaction.EventType
import spock.lang.Specification

import java.time.Instant

class AccountServiceSpec extends Specification {
    
    private AccountService service
    
    void setup() {
        Dao<Account> accountDao = Mock(Dao)
        Dao<Ledger> ledgerDao = Mock()
        service = new AccountService(accountDao, ledgerDao)
    }
    
    void 'Should create account'() {
        when:
            service.createAccount('newAccount')
            service.createAccount('newAccount', decimal(1))
        then:
            2 * service.accountDao.save(_)
    }
    
    void 'Should update account'() {
        setup:
            Account account = new Account('newAccount', decimal(1))
            Ledger ledger = new Ledger(ledgerId: 1L, accountId: account.accountId, startBalance: account.balance,
                    endBalance: decimal(1), amount: decimal(2),
                    entryTime: Instant.now(), eventType: EventType.TOP_UP)
        when:
            service.updateAccount(account, ledger)
        then:
            1 * service.accountDao.update(_)
            1 * service.ledgerDao.save(_)
    }
    
    void 'Should get All accounts'() {
        when:
            service.getAll()
        then:
            1 * service.accountDao.getAll(Account.class)
    }
    
    void 'Should determine whether there is enough money for transfer or not'() {
        setup:
            Account account = new Account('newAccount', balance)
        when:
            def result = service.isEnoughBalance(account, decimal(100))
        then:
            result == expectedResult
        where:
            balance             | expectedResult
            decimal(101) | true
            decimal(100) | true
            decimal(50)  | false
            null                | false
    }
    
    void 'Should top up the account'() {
        setup:
            Account account = new Account('newAccount', balance)
        when:
            Ledger result = service.topUp(account, decimal(100))
        then:
            result.getEndBalance() == expectedBalance
            account.getBalance() == expectedBalance
        where:
            balance             | expectedBalance
            decimal(100) | decimal(200)
    }
    
    void 'Should withdraw cash from the account'() {
        setup:
            Account account = new Account('newAccount', balance)
        when:
            Optional<Ledger> result = service.withdraw(account, decimal(100))
        then:
            result.isPresent() == expectedSuccess
            account.getBalance() == expectedBalance
        where:
            balance             | expectedBalance    | expectedSuccess
            decimal(100) | decimal(0)  | true
            decimal(50)  | decimal(50) | false
    }
}
