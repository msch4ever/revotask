package com.los.revotask.controller;

import com.los.revotask.controller.routes.UserRouteProvider;

import static com.los.revotask.util.JsonUtils.json;
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

    private void responseAsJson() {
        before((req, res) -> res.type("application/json"));
    }
}