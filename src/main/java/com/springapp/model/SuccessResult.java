package com.springapp.model;

import com.springapp.model.enums.ErrorCode;

import java.io.Serializable;

/**
 * Created by Mathieu on 6/21/2016.
 */
public class SuccessResult extends Result implements Serializable {
    private static final long serialVersionUID = 5501574335566707414L;

    public String accountIdentifier;

    public SuccessResult() {
        this.success = true;
    }

    public SuccessResult(String accountIdentifier) {
        this();
        this.accountIdentifier = accountIdentifier;
    }
}
