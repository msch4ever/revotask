package com.los.revotask.service;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import com.los.revotask.model.user.User;
import com.los.revotask.persistence.TransferDao;
import com.los.revotask.transaction.EventType;
import com.los.revotask.transaction.Transfer;
import com.los.revotask.transaction.TransferInfo;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional
public class TransferService {

    private final AccountService accountService;
    private final UserService userService;
    private final TransferDao transferDao;

    public TransferService(AccountService accountService, UserService userService, TransferDao transferDao) {
        this.accountService = accountService;
        this.userService = userService;
        this.transferDao = transferDao;
    }

    public Transfer transferMoney(long sourceUserId, long destinationUserId, BigDecimal amount) {
        User sourceUser = userService.findById(sourceUserId);
        User destinationUser = userService.findById(destinationUserId);
        validateUsers(sourceUserId, destinationUserId, sourceUser, destinationUser);
        return transferMoney(sourceUser.getAccount(), destinationUser.getAccount(), amount);
    }

    private void validateUsers(long sourceUserId, long destinationUserId, User sourceUser, User destinationUser) {
        if (sourceUser == null) {
            throw new IllegalArgumentException("Could not find source user with userId: " + sourceUserId);
        }
        if (destinationUser == null) {
            throw new IllegalArgumentException("Could not find destination user with userId: " + destinationUserId);
        }
        if (sourceUser.equals(destinationUser)) {
            throw new IllegalArgumentException("Can not perform transfer: source and destination accounts are same!");
        }
    }

    private Transfer transferMoney(Account source, Account destination, BigDecimal amount) {
        if (!accountService.isEnoughBalance(source, amount)) {
            throw new IllegalArgumentException("Not enough money on source account");
        }
        TransferInfo info = new TransferInfo(source, destination, amount);
        doMoneyMovement(source, destination, info);
        return commitTransfer(source, destination, info);
    }

    private void doMoneyMovement(Account source, Account destination, TransferInfo info) {
        source.setBalance(info.getSourceResultBalance());
        destination.setBalance(info.getDestinationResultBalance());
    }

    private Transfer commitTransfer(Account source, Account destination, TransferInfo info) {
        Transfer transfer = new Transfer(source.getAccountId(), destination.getAccountId(), info);
        Ledger sourceLedger = new Ledger(source.getAccountId(), info.getSourceStartBalance(),
                        info.getSourceResultBalance(), info.getAmount(), EventType.TRANSFER_SOURCE);
        Ledger destinationLedger = new Ledger(destination.getAccountId(), info.getDestinationStartBalance(),
                        info.getDestinationResultBalance(), info.getAmount(), EventType.TRANSFER_DESTINATION);
        accountService.updateAccount(source, sourceLedger);
        accountService.updateAccount(destination, destinationLedger);
        saveTransfer(transfer);
        return transfer;
    }

    public long saveTransfer(Transfer transfer) {
        return transferDao.save(transfer);
    }

    public List<Transfer> getAll() {
        return transferDao.getAll(Transfer.class);
    }

    public List<Transfer> findAllByAccountId(long accountId) {
        return transferDao.findAllByAccountId(accountId);
    }
}
