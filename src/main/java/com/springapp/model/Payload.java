package com.springapp.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Mathieu on 6/21/2016.
 */
public class Payload implements Serializable {
    private static final long serialVersionUID = 234053185501845866L;

    private User user;
    private Company company;
    private Account account;
    private AddonInstance addonInstance;
    private AddonBinding addonBinding;
    private Order order;
    private Notice notice;
    private HashMap<String, String> configuration = new HashMap<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AddonInstance getAddonInstance() {
        return addonInstance;
    }

    public void setAddonInstance(AddonInstance addonInstance) {
        this.addonInstance = addonInstance;
    }

    public AddonBinding getAddonBinding() {
        return addonBinding;
    }

    public void setAddonBinding(AddonBinding addonBinding) {
        this.addonBinding = addonBinding;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public HashMap<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(HashMap<String, String> configuration) {
        this.configuration = configuration;
    }
}
