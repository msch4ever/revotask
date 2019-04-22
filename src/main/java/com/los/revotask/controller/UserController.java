package com.los.revotask.controller;

import com.los.revotask.controller.routes.UserRouteProvider;

import static com.los.revotask.util.JsonUtils.json;
import static spark.Spark.*;

public class UserController extends AbstractController {

    private final UserRouteProvider routeProvider;

    public UserController(UserRouteProvider routeProvider) {
        this.routeProvider = routeProvider;
        getAll();
        findUserById();
        createUser();
        updateUser();
        deleteUser();
    }

    private void getAll() {
        get("/users", routeProvider.provideGetAllRoute(), json());
    }

    private void findUserById() {
        get("/users/:id", routeProvider.provideFindByIdRoute(), json());
    }

    private void createUser() {
        post("/users", routeProvider.provideCreateUserRoute(), json());
    }

    private void updateUser() {
        put("/users/:id", routeProvider.provideUpdateUserRoute(), json());
    }

    private void deleteUser() {
        put("/users/delete/:id", routeProvider.provideDeleteUserRoute(), json());
    }
}