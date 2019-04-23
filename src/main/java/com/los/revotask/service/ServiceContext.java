package com.los.revotask.service;

import com.los.revotask.persistence.PersistenceContext;

public class ServiceContext {

    public final UserService userService;
    public final AccountService accountService;
    public final TransferService transferService;

    public ServiceContext(final PersistenceContext persistenceContext) {
        final SessionUtils sessionUtils = new SessionUtils();
        this.userService = new UserService(persistenceContext.userDao, sessionUtils);
        this.accountService = new AccountService(persistenceContext.accountDao, persistenceContext.ledgerDao, sessionUtils);
        this.transferService = new TransferService(accountService, userService, persistenceContext.transferDao, sessionUtils);
    }
}
