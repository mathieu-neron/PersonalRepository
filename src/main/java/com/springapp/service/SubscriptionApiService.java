package com.springapp.service;

import com.springapp.model.Result;

/**
 * Created by Mathieu on 6/25/2016.
 */
public interface SubscriptionApiService {
    Result createSubscription(String url);
    Result changeSubscription(String url);
    Result cancelSubscription(String url);
    Result processSubscriptionNotice(String url);
}
