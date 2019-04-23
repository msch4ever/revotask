package com.los.revotask.controller;

import com.los.revotask.controller.routes.ResponseMessage;

import static com.los.revotask.util.JsonUtils.toJson;
import static spark.Spark.before;
import static spark.Spark.exception;

public abstract class AbstractController {

    protected void responseAsJson() {
        before((req, res) -> res.type("application/json"));
    }

    protected void handleException() {
        exception(RuntimeException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseMessage(e)));
        });
    }
}
