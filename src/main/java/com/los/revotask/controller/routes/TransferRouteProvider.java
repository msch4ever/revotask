package com.los.revotask.controller.routes;

import com.los.revotask.service.TransferService;
import spark.Route;

import java.math.BigDecimal;

public class TransferRouteProvider {

    private final TransferService service;

    public TransferRouteProvider(final TransferService service) {
        this.service = service;
    }


    public Route provideTransferRoute() {
        return (req, res) -> {
            Long sourceUserId = Long.valueOf(req.queryParams("sourceUserId"));
            Long destinationUserId = Long.valueOf(req.queryParams("destinationUserId"));
            BigDecimal amount = new BigDecimal(req.queryParams("amount"));
            return service.transferMoney(sourceUserId, destinationUserId, amount);
        };
    }
}
