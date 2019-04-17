package com.los.revotask.service;

import com.los.revotask.persistence.PersistenceContext;

public class ServiceContext {

    public final UserService userService;
    public final AccountService accountService;
    public final TransferService transferService;

    public ServiceContext(PersistenceContext persistenceContext) {
        this.userService = new UserService(persistenceContext.userDao);
        this.accountService = new AccountService(persistenceContext.accountDao, persistenceContext.ledgerDao);
        this.transferService = new TransferService(accountService, userService, persistenceContext.transferDao);
    }
}
