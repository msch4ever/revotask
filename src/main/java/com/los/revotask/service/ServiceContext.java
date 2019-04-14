package com.los.revotask.service;

import com.los.revotask.persistence.PersistanceContext;

public class ServiceContext {

    public final UserService userService;
    public final AccountService accountService;
    public final TransferService transferService;

    public ServiceContext(PersistanceContext persistanceContext) {
        this.userService = new UserService(persistanceContext.userDao);
        this.accountService = new AccountService(persistanceContext.accountDao, persistanceContext.ledgerDao);
        this.transferService = new TransferService(accountService, userService, persistanceContext.transferDao);
    }
}
