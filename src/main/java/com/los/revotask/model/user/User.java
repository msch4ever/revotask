package com.los.revotask.model.user;

import com.los.revotask.model.account.Account;

public class User {

    private static long increment = 0;

    private long userId;
    private String userName;
    private Account account;

    public User() {
    }

    public User(String userName) {
        this.userId = increment;
        this.userName = userName;
        this.account = new Account("Main account", userId);
        increment++;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return getUserId() == user.getUserId()
                && getUserName().equals(user.getUserName())
                && getAccount().equals(user.getAccount());
    }

    @Override
    public int hashCode() {
        int result = (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + getAccount().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User {userId: " + userId + ", userName: '" + userName + "', account: " + account.toString() + "}";
    }
}
