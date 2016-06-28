package com.springapp.controller;

import com.springapp.model.ErrorResult;
import com.springapp.model.Result;
import com.springapp.model.enums.ErrorCode;
import com.springapp.service.UserApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mathieu on 6/25/2016.
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserApiService userApiService;

    @RequestMapping(value= "/assign")
    public ResponseEntity<Result> assignUser(@RequestParam("url") String url) {
        try {
            Result result = userApiService.assignUser(url);
            return ok(result);
        } catch (Exception e) {
            logger.error("Exception thrown", e);
            return ok(new ErrorResult(ErrorCode.UNKNOWN_ERROR, e.getMessage()));
        }
    }

    @RequestMapping(value= "/unassign")
    public ResponseEntity<Result> unassignUser(@RequestParam("url") String url) {
        try {
            Result result = userApiService.unassignUser(url);
            return ok(result);
        } catch (Exception e) {
            logger.error("Exception thrown", e);
            return ok(new ErrorResult(ErrorCode.UNKNOWN_ERROR, e.getMessage()));
        }
    }
}
