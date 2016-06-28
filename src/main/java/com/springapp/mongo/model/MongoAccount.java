package com.springapp.mongo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 6/21/2016.
 */
@Document
public class MongoAccount {
    @Id
    private String id;
    private String status;
    private String edition;

    private List<MongoUser> users = new ArrayList<>();

    public MongoAccount() {}

    public MongoAccount(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MongoUser> getUsers() {
        return users;
    }

    public void setUsers(List<MongoUser> users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addUser(MongoUser user) {
        users.add(user);
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}
