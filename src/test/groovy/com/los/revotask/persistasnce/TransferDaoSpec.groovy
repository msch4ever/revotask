package com.los.revotask.persistasnce

import com.los.revotask.model.account.Account
import com.los.revotask.persistence.TransferDao
import com.los.revotask.service.SessionUtils
import com.los.revotask.transaction.Transfer
import com.los.revotask.transaction.TransferInfo
import spock.lang.Specification

import static com.los.revotask.TestUtils.cleanTables
import static com.los.revotask.TestUtils.decimal

class TransferDaoSpec extends Specification {
    
    private SessionUtils sessionUtils
    private TransferDao dao
    
    void setup() {
        sessionUtils = new SessionUtils()
        sessionUtils.openAtomicTask()
        dao = new TransferDao()
    
    }
    
    void cleanup() {
        sessionUtils.commitAndCloseSession()
        cleanTables()
    }
    
    void 'Should find all transfers related to account'() {
        setup:
            Account source  = new Account(accountId: 1L, accountName: 'source', balance: decimal(100))
            Account destination = new Account(accountId: 2L, accountName: 'destination', balance: decimal(900))
        
            Transfer transfer1 = new Transfer(source.accountId, destination.accountId, new TransferInfo(source, destination, decimal(100)))
            Transfer transfer2 = new Transfer(source.accountId, destination.accountId, new TransferInfo(source, destination, decimal(100)))
            Transfer transfer3 = new Transfer(source.accountId, destination.accountId, new TransferInfo(source, destination, decimal(100)))
            Transfer transfer4 = new Transfer(source.accountId, destination.accountId, new TransferInfo(source, destination, decimal(100)))
        
            dao.save(transfer1)
            dao.save(transfer2)
            dao.save(transfer3)
            dao.save(transfer4)
        when:
            List<Transfer> sourceTransfers = dao.findAllByAccountId(source.accountId)
            List<Transfer> destiantionTransfers = dao.findAllByAccountId(destination.accountId)
        then:
            sourceTransfers.size() == 4
            destiantionTransfers.size() == 4
            sourceTransfers.containsAll(destiantionTransfers)
            sourceTransfers == [transfer1, transfer2, transfer3, transfer4]
            destiantionTransfers == [transfer1, transfer2, transfer3, transfer4]
    }
}
