package com.springapp.model;

import com.springapp.mongo.model.MongoUser;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Mathieu on 6/21/2016.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 8225110915191406519L;

    private String id;
    private String uuid;
    private String openId;
    private String email;
    private String firstName;
    private String lastName;
    private String language;
    private Map<String, String> attributes;

    private User() {
    }

    public User(String id,
                String uuid,
                String openId,
                String email,
                String firstName,
                String lastName,
                String language,
                Map<String, String> attributes) {
        this.id = id;
        this.uuid = uuid;
        this.openId = openId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.attributes = attributes;
    }

    public User(MongoUser user) {
        super();
        this.id = user.getId();
        this.uuid = user.getUuid();
        this.openId = user.getOpenId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.language = user.getLanguage();
    }

    public MongoUser toMongoUser() {
        MongoUser mongoUser = new MongoUser();
        mongoUser.setId(id);
        mongoUser.setUuid(uuid);
        mongoUser.setOpenId(openId);
        mongoUser.setEmail(email);
        mongoUser.setFirstName(firstName);
        mongoUser.setLastName(lastName);
        mongoUser.setLastName(language);
        return mongoUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }
}
