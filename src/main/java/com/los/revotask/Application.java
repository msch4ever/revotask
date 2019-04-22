package com.los.revotask;

import com.los.revotask.controller.TransferController;
import com.los.revotask.controller.UserController;
import com.los.revotask.controller.routes.TransferRouteProvider;
import com.los.revotask.controller.routes.UserRouteProvider;
import com.los.revotask.persistence.PersistenceContext;
import com.los.revotask.service.ServiceContext;

public class Application {

    public static void main(String[] args) {
        PersistenceContext persistenceContext = new PersistenceContext();
        ServiceContext serviceContext = new ServiceContext(persistenceContext);
        UserRouteProvider userRouteProvider = new UserRouteProvider(serviceContext.userService);
        TransferRouteProvider transferRouteProvider = new TransferRouteProvider(serviceContext.transferService);
        new UserController(userRouteProvider);
        new TransferController(transferRouteProvider);
        println("Application started");
    }

    private static void println(String line) {
        System.out.println(line);
    }
}
