package com.springapp.service.validator;

import com.springapp.model.enums.SubscriptionStatus;
import org.springframework.stereotype.Service;

/**
 * Created by Mathieu on 6/19/2016.
 */
@Service
public class SubscriptionValidatorService {
    public Boolean validateSubscriptionStatusChange(SubscriptionStatus currentStatus, SubscriptionStatus newStatus) {
        if (SubscriptionStatus.possibleStatusChange.get(currentStatus).contains(newStatus)) {
            return true;
        }
        return false;
    }
}
