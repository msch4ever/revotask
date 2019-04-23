package com.los.revotask.service;

import com.los.revotask.persistence.PersistenceContext;

public class ServiceContext {

    public final UserService userService;
    public final AccountService accountService;
    public final TransferService transferService;

    public ServiceContext(PersistenceContext persistenceContext) {
        this.userService = new UserService(persistenceContext);
        this.accountService = new AccountService(persistenceContext);
        this.transferService = new TransferService(accountService, userService, persistenceContext);
    }
}
