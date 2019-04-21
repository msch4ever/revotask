package com.los.revotask.controller;

import com.los.revotask.controller.routes.ResponseMessage;
import com.los.revotask.controller.routes.UserRouteProvider;

import static com.los.revotask.util.JsonUtils.json;
import static com.los.revotask.util.JsonUtils.toJson;
import static spark.Spark.*;

public class UserController {

    private final UserRouteProvider userRouteProvider;

    public UserController(UserRouteProvider userRouteProvider) {
        this.userRouteProvider = userRouteProvider;
        responseAsJson();
        getAll();
        findUserById();
        createUser();
        updateUser();
        deleteUser();
        handleException();
    }

    private void getAll() {
        get("/users", userRouteProvider.provideGetAllRoute(), json());
    }

    private void findUserById() {
        get("/users/:id", userRouteProvider.provideFindByIdRoute(), json());
    }

    private void createUser() {
        post("/users", userRouteProvider.provideCreateUserRoute(), json());
    }

    private void updateUser() {
        put("/users/:id", userRouteProvider.provideUpdateUserRoute(), json());
    }

    private void deleteUser() {
        put("/users/delete/:id", userRouteProvider.provideDeleteUserRoute(), json());
    }
    private void responseAsJson() {
        before((req, res) -> res.type("application/json"));
    }

    private void handleException() {
        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseMessage(e)));
        });
    }
}