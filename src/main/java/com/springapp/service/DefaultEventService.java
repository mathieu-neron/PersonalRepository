package com.springapp.service;

import com.springapp.model.Result;
import com.springapp.model.SuccessResult;
import org.springframework.stereotype.Service;

/**
 * Created by Mathieu on 6/21/2016.
 */
@Service
public class DefaultEventService implements EventService {
    public Result process(String eventUrl, String token) {
        return new SuccessResult("placeholder");
    }
}

