package com.los.revotask.controller.routes;

public class UserResponseError {

    private String message;

    public UserResponseError(String message, String... args) {
        this.message = String.format(message, args);
    }

    public UserResponseError(Exception e) {
        this.message = e.getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}