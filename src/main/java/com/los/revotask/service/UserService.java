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

    public User createUser(String userName, String accountName, BigDecimal accountAmount) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("userName can not be null");
        }
        User newUser;
        if (accountAmount != null && accountName != null && !accountName.isEmpty()) {
            newUser = new User(userName, accountName, accountAmount);
        } else {
            newUser = new User(userName);
        }
        userDao.save(newUser);
        return newUser;
    }

    public User findById(long id) {
        return userDao.findById(User.class, id);
    }

    public List<User> findByName(String userName) {
        return userDao.findByName(userName);
    }

    public List<User> getAll() {
        return userDao.getAll(User.class);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public User update(Long userId, String newUserName) {
        if (newUserName == null || newUserName.isEmpty()) {
            throw new IllegalArgumentException("new userName can not be null");
        }
        User userToUpdate = userDao.findById(User.class, userId);
        if (userToUpdate == null) {
            throw new IllegalArgumentException("could not find user with userId= " + userId);
        }
        userToUpdate.setUserName(newUserName);
        userDao.update(userToUpdate);
        return userToUpdate;
    }
}
