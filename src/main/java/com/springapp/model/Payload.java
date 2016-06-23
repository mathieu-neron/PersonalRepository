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
}
