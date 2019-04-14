package com.los.revotask.service;

import com.los.revotask.model.account.Account;
import com.los.revotask.model.account.Ledger;
import com.los.revotask.transaction.Transfer;
import com.los.revotask.transaction.TransferInfo;
import com.los.revotask.util.EventType;

import java.math.BigDecimal;

public class TransferService {

    private AccountService accountService;

    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    public boolean transferMoney(Account source, Account destination, BigDecimal amount) {
        if (!accountService.isEnoughBalance(source, amount)) {
            return false;
        }

        TransferInfo info = new TransferInfo(source, destination, amount);
        doMoneyMovement(source, destination, info);
        
        if (!validateTransfer(source, destination, info, amount)) {
            return rollbackTransfer(source, destination, info);
        }
        return commitTransfer(source, destination, info);
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

    private boolean commitTransfer(Account source, Account destination, TransferInfo info) {
        //update in accounts in DB
        System.out.println(new Transfer(source.getAccountId(), destination.getAccountId(), info));
        System.out.println(
                new Ledger(source.getAccountId(), info.getSourceStartBalance(),
                        info.getSourceResultBalance(), info.getAmount(), EventType.TRANSFER_SOURCE));
        System.out.println(new Ledger(destination.getAccountId(), info.getDestinationStartBalance(),
                info.getDestinationResultBalance(), info.getAmount(), EventType.TRANSFER_DESTINATION));
        return true;
    }

}
