package com.springapp.model;

import com.springapp.model.enums.EventFlag;
import com.springapp.model.enums.EventType;

import java.io.Serializable;

/**
 * Created by Mathieu on 6/21/2016.
 */
public class Event implements Serializable {
    private static final long serialVersionUID = -3530816054587950088L;

    private EventType type;
    private Marketplace marketplace;
    private String applicationUuid;
    private EventFlag flag;
    private User creator;
    private Payload payload;
    private String returnUrl;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public String getApplicationUuid() {
        return applicationUuid;
    }

    public void setApplicationUuid(String applicationUuid) {
        this.applicationUuid = applicationUuid;
    }

    public EventFlag getFlag() {
        return flag;
    }

    public void setFlag(EventFlag flag) {
        this.flag = flag;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
