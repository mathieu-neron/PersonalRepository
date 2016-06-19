package com.springapp.validator;

import com.springapp.model.enums.SubscriptionStatus;

/**
 * Created by Mathieu on 6/19/2016.
 */
public class SubscriptionValidator {
    public void validateSubscriptionStatusChange(SubscriptionStatus currentStatus, SubscriptionStatus newStatus) {
        if (!SubscriptionStatus.possibleStatusChange.get(currentStatus).contains(newStatus)) {
            throw new RuntimeException();
        }
    }
}
