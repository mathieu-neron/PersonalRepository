package com.springapp.model;

import com.springapp.model.enums.NoticeType;

import java.io.Serializable;

/**
 * Created by Mathieu on 6/21/2016.
 */
public class Notice implements Serializable {
    private static final long serialVersionUID = 1225518156129098636L;

    private NoticeType type;
    private String message;
}
