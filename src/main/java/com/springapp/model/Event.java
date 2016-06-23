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
}
