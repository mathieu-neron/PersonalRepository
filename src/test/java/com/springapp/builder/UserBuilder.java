package com.springapp.builder;

import com.springapp.model.User;

import java.util.Map;

public class UserBuilder {
    private String id;
    private String uuid;
    private String openId;
    private String email;
    private String firstName;
    private String lastName;
    private String language;
    private Map<String, String> attributes;

    public UserBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserBuilder withOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withLanguage(String language) {
        this.language = language;
        return this;
    }

    public UserBuilder withAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }

    public User build() {
        return new User(id, uuid, openId, email, firstName, lastName, language, attributes);
    }
}