package com.springapp.model;

import com.springapp.model.enums.ErrorCode;

import java.io.Serializable;

/**
 * Created by Mathieu on 6/21/2016.
 */
public class ErrorResult extends Result implements Serializable {
    private static final long serialVersionUID = -6634747339853025618L;

    public ErrorCode errorCode;
    public String message;

    public ErrorResult(ErrorCode errorCode, String message) {
        this.success = false;
        this.errorCode = errorCode;
        this.message = message;
    }
}
