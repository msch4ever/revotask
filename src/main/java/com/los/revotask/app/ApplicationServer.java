package com.los.revotask.app;

import com.los.revotask.controller.TransferController;
import com.los.revotask.controller.UserController;
import com.los.revotask.controller.routes.TransferRouteProvider;
import com.los.revotask.controller.routes.UserRouteProvider;
import com.los.revotask.persistence.PersistenceContext;
import com.los.revotask.service.ServiceContext;
import spark.Spark;

public class ApplicationServer {

    public static void startServer() {
        final PersistenceContext persistenceContext = new PersistenceContext();
        final ServiceContext serviceContext = new ServiceContext(persistenceContext);
        final UserRouteProvider userRouteProvider = new UserRouteProvider(serviceContext.userService);
        final TransferRouteProvider transferRouteProvider = new TransferRouteProvider(serviceContext.transferService);
        new UserController(userRouteProvider);
        new TransferController(transferRouteProvider);
    }

    public static void stopServer() {
        Spark.stop();
    }
}
