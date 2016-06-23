package com.springapp.model;

import java.io.Serializable;

/**
 * Created by Mathieu on 6/21/2016.
 */
public class Company implements Serializable {
    private static final long serialVersionUID = 7280906235303577101L;

    private String uuid;
    private String name;
    private String email;
    private String phoneNumber;
    private String website;
    private String country;
}
