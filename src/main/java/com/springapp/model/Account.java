package com.springapp.model;

import com.springapp.mongo.model.MongoAccount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mathieu on 6/19/2016.
 */
public class Account implements Serializable {
    private static final long serialVersionUID = -5511400795782437006L;

    private String id;
    private String status;
    private List<User> users = new ArrayList<>();

    public Account(String id) {
        this.id = id;
    }

    public Account(String id, String status, List<User> users) {
        this.id = id;
        this.status = status;
        this.users = users;
    }

    public Account(MongoAccount account) {
        super();
        this.id = account.getId();
        this.status = account.getStatus();
        this.users.addAll(account.getUsers().stream()
                .map(User::new)
                .collect(Collectors.toList()));
    }

    public MongoAccount toMongoAccount() {
        MongoAccount account = new MongoAccount();
        account.setId(id);
        account.setStatus(status);
        account.setUsers(users.stream()
                .map(User::toMongoUser)
                .collect(Collectors.toList()));
        return account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
