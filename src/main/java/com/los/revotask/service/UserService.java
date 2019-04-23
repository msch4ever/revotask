package com.los.revotask.service;

import com.los.revotask.model.user.User;
import com.los.revotask.persistence.UserDao;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional(Transactional.TxType.SUPPORTS)
public class UserService {

    private final UserDao userDao;
    private final SessionUtils sessionUtils;

    public UserService(UserDao userDao, SessionUtils sessionUtils) {
        this.userDao = userDao;
        this.sessionUtils = sessionUtils;
    }

    public User createUser(String userName, String accountName, BigDecimal accountAmount) {
        sessionUtils.openAtomicTask();
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
        sessionUtils.commitAndCloseSession();
        return newUser;
    }

    public User findById(long id) {
        User resultUser = userDao.findById(User.class, id);
        if (resultUser == null) {
            throw new IllegalArgumentException("Could not find user with userId: " + id);
        }
        return resultUser;
    }

    public User findByIdAtomic(long id) {
        sessionUtils.openAtomicTask();
        User result = findById(id);
        sessionUtils.commitAndCloseSession();
        return result;
    }

    public List<User> findByName(String userName) {
        sessionUtils.openAtomicTask();
        List<User> resultList = userDao.findByName(userName);
        if (resultList == null || resultList.isEmpty()) {
            throw new IllegalArgumentException("Could not find users with userName: " + userName);
        }
        sessionUtils.commitAndCloseSession();
        return resultList;
    }

    public List<User> getAll() {
        sessionUtils.openAtomicTask();
        List<User> resultList = userDao.getAll(User.class);
        sessionUtils.commitAndCloseSession();
        return resultList;
    }

    public void delete(Long userId) {
        sessionUtils.openAtomicTask();
        User userToDelete = userDao.findById(User.class, userId);
        if (userToDelete == null) {
            throw new IllegalArgumentException("Could not find user with userId: " + userId);
        }
        userDao.delete(userToDelete);
        sessionUtils.commitAndCloseSession();
    }

    public User update(Long userId, String newUserName) {
        sessionUtils.openAtomicTask();
        if (newUserName == null || newUserName.isEmpty()) {
            throw new IllegalArgumentException("new userName can not be null");
        }
        User userToUpdate = findById(userId);
        userToUpdate.setUserName(newUserName);
        userDao.update(userToUpdate);
        sessionUtils.commitAndCloseSession();
        return userToUpdate;
    }
}
