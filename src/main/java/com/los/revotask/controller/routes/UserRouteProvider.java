package com.los.revotask.controller.routes;

import com.los.revotask.model.user.User;
import com.los.revotask.service.UserService;
import spark.Route;

import java.math.BigDecimal;


public class UserRouteProvider {

    private final UserService userService;

    public UserRouteProvider(UserService userService) {
        this.userService = userService;
    }

    public Route provideGetAllRoute() {
        return (req, res) -> userService.getAll();
    }

    public Route provideFindByIdRoute() {
        return (req, res) -> {
            String id = req.params(":id");
            User user = userService.findByIdAtomic(Long.valueOf(req.params(":id")));
            if (user != null) {
                return user;
            }
            res.status(400);
            return new ResponseMessage("No user with id :%s found", id);
        };
    }

    public Route provideCreateUserRoute() {
        return (req, res) -> {
            String userName = req.queryParams("userName");
            String accountName = req.queryParams("accountName");
            BigDecimal accountAmount = new BigDecimal(req.queryParams("accountAmount"));
            return userService.createUser(userName, accountName, accountAmount);
        };
    }

    public Route provideUpdateUserRoute() {
        return (req, res) -> {
            Long userId = Long.valueOf(req.params(":id"));
            String newUserName = req.queryParams("newUserName");
            return userService.update(userId, newUserName);
        };
    }

    public Route provideDeleteUserRoute() {
        return (req, res) -> {
            Long userId = Long.valueOf(req.params(":id"));
            userService.delete(userId);
            return new ResponseMessage("User with userId: " + userId + " has been successfully deleted!");
        };
    }
}
