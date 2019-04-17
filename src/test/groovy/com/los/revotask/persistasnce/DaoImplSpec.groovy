package com.los.revotask.persistasnce

import com.los.revotask.model.account.Account
import com.los.revotask.model.account.Ledger
import com.los.revotask.model.user.User
import com.los.revotask.persistence.Dao
import com.los.revotask.persistence.DaoImpl
import com.los.revotask.persistence.UserDao
import com.los.revotask.util.EventType
import spock.lang.Specification

import static com.los.revotask.TestUtils.cleanTables
import static com.los.revotask.TestUtils.decimal

class DaoImplSpec extends Specification {
    
    void setup() {
        cleanTables()
    }
    
    void 'Should save entity to DB and find it by id'() {
        setup:
            Dao<User> dao = new UserDao()
            User givenUser = new User('userName', 'account', decimal(100))
        when:
            def result = dao.save(givenUser)
        then:
            result == 1L
        and:
            User resultUser = dao.findById(User.class, result)
            resultUser.userId == result
            resultUser.getUserName() == givenUser.getUserName()
            resultUser.getAccount() == givenUser.getAccount()
    }
    
    void 'Should fetch all entities from DB'() {
        setup:
            Dao<Ledger> ledgerDao = new DaoImpl<>()
            
            Ledger ledger1 = new Ledger(1L, decimal(100), decimal(200), decimal(100), EventType.TOP_UP)
            Ledger ledger2 = new Ledger(1L, decimal(200), decimal(300), decimal(100), EventType.TOP_UP)
            Ledger ledger3 = new Ledger(1L, decimal(300), decimal(400), decimal(100), EventType.TRANSFER_DESTINATION)
            Ledger ledger4 = new Ledger(1L, decimal(400), decimal(500), decimal(100), EventType.TOP_UP)
            
            ledgerDao.save(ledger1)
            ledgerDao.save(ledger2)
            ledgerDao.save(ledger3)
            ledgerDao.save(ledger4)
        when:
            List<Ledger> result = ledgerDao.getAll(Ledger.class)
        then:
            result.size() == 4
            result.containsAll([ledger1, ledger2, ledger3, ledger4])
    }
    
    void 'Should update entity'() {
        setup:
            Dao<Account> accountDao = new DaoImpl<>()
            Account givenAccount = new Account('accountName')
            accountDao.save(givenAccount)
            BigDecimal givenBalance = givenAccount.getBalance()
        when:
            givenAccount.setBalance(decimal(100))
            accountDao.update(givenAccount)
            Account resultAccount = accountDao.findById(Account.class, givenAccount.getAccountId())
        then:
            resultAccount.accountId == givenAccount.getAccountId()
            resultAccount.getBalance().compareTo(givenBalance) != 0
            resultAccount.getBalance().compareTo(decimal(100)) == 0
    }
    
    void 'Shuold delete entity from the DB'() {
        setup:
            Dao<Ledger> ledgerDao = new DaoImpl()
            Ledger givenLedger = new Ledger(1L, decimal(100), decimal(200), decimal(100), EventType.TOP_UP)
            ledgerDao.save(givenLedger)
        when:
            ledgerDao.delete(givenLedger)
        then:
            def resultLEdger = ledgerDao.findById(Ledger.class, givenLedger.getLedgerId())
            !resultLEdger
    }
    
    
}
