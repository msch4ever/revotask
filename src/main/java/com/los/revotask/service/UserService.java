package com.los.revotask.service;

import com.los.revotask.model.user.User;
import com.los.revotask.persistence.UserDao;

import java.math.BigDecimal;
import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public long createUser(String userName, String accountName, BigDecimal accountAmount) {
        User newUser = new User(userName, accountName, accountAmount);
        return userDao.save(newUser);
    }

    public User findById(long id) {
        return userDao.findById(User.class, id);
    }

    public List<User> getAll() {
        return userDao.getAll(User.class);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void update(User user) {
        userDao.update(user);
    }
}
