package com.springapp.service;

import com.springapp.model.Result;
import com.springapp.model.SuccessResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Mathieu on 6/25/2016.
 */
public interface UserApiService {
    Result assignUser(String url);
    Result unassignUser(String url);
}
