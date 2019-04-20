package com.los.revotask;

import com.los.revotask.controller.UserController;
import com.los.revotask.controller.routes.UserRouteProvider;
import com.los.revotask.persistence.UserDao;
import com.los.revotask.service.UserService;

public class Application {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);
        UserRouteProvider userRouteProvider = new UserRouteProvider(userService);
        new UserController(userRouteProvider);

        println("Application started");
    }

    private static void println(String line) {
        System.out.println(line);
    }
}
