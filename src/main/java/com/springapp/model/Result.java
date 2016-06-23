package com.springapp.model;

import com.springapp.model.enums.ErrorCode;

import java.io.Serializable;

/**
 * Created by Mathieu on 6/21/2016.
 */
public abstract class Result implements Serializable {
    private static final long serialVersionUID = -8812416854592034015L;

    protected boolean success;
}
