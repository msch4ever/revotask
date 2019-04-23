package com.los.revotask.service;

import com.los.revotask.model.user.User;
import com.los.revotask.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional(Transactional.TxType.SUPPORTS)
public class UserService extends AbstractService {

    public UserService(PersistenceContext persistenceContext) {
        super(persistenceContext);
    }

    public User createUser(String userName, String accountName, BigDecimal accountAmount) {
        openAtomicTask();
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("userName can not be null");
        }
        User newUser;
        if (accountAmount != null && accountName != null && !accountName.isEmpty()) {
            newUser = new User(userName, accountName, accountAmount);
        } else {
            newUser = new User(userName);
        }
        getUserDao().save(newUser);
        int debug = 1;
        if (debug != 1) {
            throw new RuntimeException();
        }
        commitAndCloseSession();
        return newUser;
    }

    public User findById(long id) {
        User resultUser = getUserDao().findById(User.class, id);
        if (resultUser == null) {
            throw new IllegalArgumentException("Could not find user with userId: " + id);
        }
        return resultUser;
    }

    public User findByIdAtomic(long id) {
        openAtomicTask();
        User result = findById(id);
        commitAndCloseSession();
        return result;
    }

    public List<User> findByName(String userName) {
        openAtomicTask();
        List<User> resultList = getUserDao().findByName(userName);
        if (resultList == null || resultList.isEmpty()) {
            throw new IllegalArgumentException("Could not find users with userName: " + userName);
        }
        commitAndCloseSession();
        return resultList;
    }

    public List<User> getAll() {
        openAtomicTask();
        List<User> resultList = getUserDao().getAll(User.class);
        commitAndCloseSession();
        return resultList;
    }

    public void delete(Long userId) {
        openAtomicTask();
        User userToDelete = getUserDao().findById(User.class, userId);
        if (userToDelete == null) {
            throw new IllegalArgumentException("Could not find user with userId: " + userId);
        }
        getUserDao().delete(userToDelete);
        commitAndCloseSession();
    }

    public User update(Long userId, String newUserName) {
        openAtomicTask();
        if (newUserName == null || newUserName.isEmpty()) {
            throw new IllegalArgumentException("new userName can not be null");
        }
        User userToUpdate = findById(userId);
        userToUpdate.setUserName(newUserName);
        getUserDao().update(userToUpdate);
        commitAndCloseSession();
        return userToUpdate;
    }
}
