package com.springapp.model.builder;

import com.springapp.model.Account;
import com.springapp.model.User;

import java.util.List;

public class AccountBuilder {
    private String id;
    private String status;
    private List<User> users;

    public AccountBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public AccountBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public AccountBuilder withUsers(List<User> users) {
        this.users = users;
        return this;
    }

    public Account build() {
        return new Account(id, status, users);
    }
}