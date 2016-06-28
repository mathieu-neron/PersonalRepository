package com.springapp.controller;

import com.springapp.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Mathieu on 6/25/2016.
 */
public abstract class BaseController {
    public ResponseEntity<Result> ok(Result result) {
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
