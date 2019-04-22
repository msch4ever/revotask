package com.los.revotask.controller;

import com.los.revotask.controller.routes.TransferRouteProvider;

import static com.los.revotask.util.JsonUtils.json;
import static spark.Spark.put;


public class TransferController extends AbstractController {

    private final TransferRouteProvider routeProvider;

    public TransferController(TransferRouteProvider routeProvider) {
        this.routeProvider = routeProvider;
        responseAsJson();
        transferMoney();
        handleException();
    }

    private void transferMoney() {
        put("/transfer", routeProvider.provideTransferRoute(), json());
    }

}
