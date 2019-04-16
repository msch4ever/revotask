package com.los.revotask.service

import com.los.revotask.model.account.Account
import com.los.revotask.model.account.Ledger
import com.los.revotask.persistence.Dao
import spock.lang.Specification

class AccountServiceSpec extends Specification {

    AccountService service
    Account account

    void setup() {
        account = new Account(accountId: 1L)
        Dao<Account> accountDao = Mock(Dao)
        Dao<Ledger> ledgerDao = Mock()
        service = new AccountService(accountDao, ledgerDao)
    }

    void "Should create account"() {
        when:
            service.createAccount('newAccount')
        then:
            1 * service.accountDao.save(_)


    }
}
