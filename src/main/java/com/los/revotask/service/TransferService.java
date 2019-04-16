package com.los.revotask.service;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import com.los.revotask.model.user.User;
import com.los.revotask.persistence.TransferDao;
import com.los.revotask.transaction.Transfer;
import com.los.revotask.transaction.TransferInfo;
import com.los.revotask.util.EventType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    public Optional<Transfer> transferMoney(long sourceUserId, long destinationUserId, BigDecimal amount) {
        User sourceUser = userService.findById(sourceUserId);
        User destinationUser = userService.findById(destinationUserId);
        return transferMoney(sourceUser.getAccount(), destinationUser.getAccount(), amount);
    }

    private Optional<Transfer> transferMoney(Account source, Account destination, BigDecimal amount) {
        if (!accountService.isEnoughBalance(source, amount)) {
            return Optional.empty();
        }

        TransferInfo info = new TransferInfo(source, destination, amount);
        doMoneyMovement(source, destination, info);

        if (!validateTransfer(source, destination, info, amount)) {
            rollbackTransfer(source, destination, info);
            return Optional.empty();
        }
        return Optional.of(commitTransfer(source, destination, info));
    }

    private void doMoneyMovement(Account source, Account destination, TransferInfo info) {
        source.setBalance(info.getSourceResultBalance());
        destination.setBalance(info.getDestinationResultBalance());
    }

    private boolean validateTransfer(Account source, Account destination, TransferInfo info, BigDecimal amount) {
        boolean sourceAddition = source.getBalance().add(amount).equals(info.getSourceStartBalance());
        boolean destinationSubtraction =
                destination.getBalance().subtract(amount).equals(info.getDestinationStartBalance());
        boolean valid = sourceAddition && destinationSubtraction;
        return valid || rollbackTransfer(source, destination, info);
    }

    private boolean rollbackTransfer(Account source, Account destination, TransferInfo info) {
        source.setBalance(info.getSourceStartBalance());
        destination.setBalance(info.getDestinationStartBalance());
        return false;
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
