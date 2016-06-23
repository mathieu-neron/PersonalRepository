package com.springapp.service;

import com.springapp.model.Result;

/**
 * Created by Mathieu on 6/21/2016.
 */
public interface EventService {
    public Result process(String eventUrl, String token);
}

