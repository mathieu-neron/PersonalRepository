package com.springapp.service;

import com.springapp.builder.UserBuilder;
import com.springapp.model.*;
import com.springapp.model.enums.EventType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Mathieu on 6/26/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultUserApiServiceTest {

    private UserApiService classToTest;

    @Mock
    private AccountService accountService;
    @Mock
    private AppDirectClient appDirectClient;

    @Before
    public void setUp() {
        classToTest = new DefaultUserApiService(accountService, appDirectClient);
    }

    @Test
    public void whenCreatingSubscriptionThenResultShouldBeSuccessful() {
        final String url = "url";
        final String accountIdentifier = "accountIdentifier";
        final User user = new UserBuilder().withOpenId("openId").withUuid("uuid").withEmail("mathieu.neron@appdirect.com").withFirstName("Mathieu").withLastName("Neron").withLanguage("FR").build();
        Event event = new Event();
        Account account = new Account(accountIdentifier);
        Payload payload = new Payload();
        payload.setUser(user);
        payload.setAccount(account);
        event.setPayload(payload);

        SuccessResult expectedResult = new SuccessResult();

        when(appDirectClient.getEvent(url, EventType.USER_ASSIGNMENT)).thenReturn(event);
        when(accountService.isUserWithUserUidPresent(user.getUuid())).thenReturn(false);
        when(accountService.createUser(user, accountIdentifier)).thenReturn(expectedResult);

        SuccessResult actualResult = (SuccessResult) classToTest.assignUser(url);

        assertTrue(actualResult.isSuccess());
    }

    @Test
    public void whenChangingSubscriptionThenResultShouldBeSuccessful() {
        final String url = "url";
        final String accountIdentifier = "accountIdentifier";
        final User user = new UserBuilder().withOpenId("openId").withUuid("uuid").withEmail("mathieu.neron@appdirect.com").withFirstName("Mathieu").withLastName("Neron").withLanguage("FR").build();
        Event event = new Event();
        Account account = new Account(accountIdentifier);
        Payload payload = new Payload();
        payload.setUser(user);
        payload.setAccount(account);
        event.setPayload(payload);

        SuccessResult expectedResult = new SuccessResult();

        when(appDirectClient.getEvent(url, EventType.USER_UNASSIGNMENT)).thenReturn(event);
        when(accountService.deleteUser(user.getUuid())).thenReturn(expectedResult);

        SuccessResult actualResult = (SuccessResult) classToTest.unassignUser(url);

        assertTrue(actualResult.isSuccess());
    }
}