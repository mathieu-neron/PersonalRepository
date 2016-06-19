package com.springapp.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mathieu on 6/19/2016.
 */
public enum SubscriptionStatus {
    FREE_TRIAL, FREE_TRIAL_EXPIRED, ACTIVE, SUSPENDED, CANCELLED;

    public static final Map<SubscriptionStatus, List<SubscriptionStatus>> possibleStatusChange;
    static {
        possibleStatusChange = new HashMap<SubscriptionStatus, List<SubscriptionStatus>>();
        possibleStatusChange.put(FREE_TRIAL, Arrays.asList(FREE_TRIAL, ACTIVE, CANCELLED));
        possibleStatusChange.put(FREE_TRIAL_EXPIRED, Arrays.asList(ACTIVE, CANCELLED));
        possibleStatusChange.put(ACTIVE, Arrays.asList(SUSPENDED, CANCELLED));
        possibleStatusChange.put(SUSPENDED, Arrays.asList(ACTIVE, CANCELLED));
        possibleStatusChange.put(CANCELLED, Collections.<SubscriptionStatus>emptyList());
    }
}
