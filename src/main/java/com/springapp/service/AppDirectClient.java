package com.springapp.service;

import com.springapp.model.Event;
import com.springapp.model.enums.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Mathieu on 6/23/2016.
 */
@Component
public class AppDirectClient {

    @Autowired
    private ProtectedResourceDetails protectedResourceDetails;

    public Event getEvent(String url, EventType eventType) {
        final OAuthRestTemplate oAuthRestTemplate = new OAuthRestTemplate(protectedResourceDetails);
        Event event = oAuthRestTemplate.getForObject(url, Event.class);

        if (event.getType().equals(eventType)) {
            return event;
        }

        throw new RuntimeException(String.format("Incorrect event type: %s expected, %s actual.", eventType, event.getType()));
    }
}
