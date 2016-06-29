package com.springapp.service;

import com.springapp.model.builder.UserBuilder;
import com.springapp.model.*;
import com.springapp.model.enums.EventType;
import com.springapp.model.enums.NoticeType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by Mathieu on 6/26/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultSubscriptionApiServiceTest {

    private SubscriptionApiService classToTest;

    @Mock
    private AccountService accountService;
    @Mock
    private AppDirectClient appDirectClient;

    @Before
    public void setUp() {
        classToTest = new DefaultSubscriptionApiService(accountService, appDirectClient);
    }

    @Test
    public void whenCreatingSubscriptionThenResultShouldBeSuccessful() {
        final String url = "url";
        final String accountIdentifier = "accountIdentifier";
        final User creator = new UserBuilder().withOpenId("openId").withUuid("uuid").withEmail("mathieu.neron@appdirect.com").withFirstName("Mathieu").withLastName("Neron").withLanguage("FR").build();
        Event event = new Event();
        event.setCreator(creator);

        SuccessResult expectedResult = new SuccessResult(accountIdentifier);

        when(appDirectClient.getEvent(url, EventType.SUBSCRIPTION_ORDER)).thenReturn(event);
        when(accountService.isUserWithUserUidPresent(creator.getUuid())).thenReturn(false);
        when(accountService.createAccount(creator)).thenReturn(expectedResult);

        SuccessResult actualResult = (SuccessResult) classToTest.createSubscription(url);

        assertTrue(actualResult.isSuccess());
        assertThat(expectedResult.getAccountIdentifier(), is(actualResult.getAccountIdentifier()));
    }

    @Test
    public void whenChangingSubscriptionThenResultShouldBeSuccessful() {
        final String url = "url";
        final String accountIdentifier = "accountIdentifier";
        final String status = "status";
        final String edition = "editionCode";
        Event event = new Event();
        Payload payload = new Payload();
        Account account = new Account(accountIdentifier);
        account.setStatus(status);
        payload.setAccount(account);
        Order order = new Order();
        order.setEditionCode(edition);
        payload.setOrder(order);
        event.setPayload(payload);

        SuccessResult expectedResult = new SuccessResult();

        when(appDirectClient.getEvent(url, EventType.SUBSCRIPTION_CHANGE)).thenReturn(event);
        when(accountService.updateAccount(account, edition)).thenReturn(expectedResult);

        SuccessResult actualResult = (SuccessResult) classToTest.changeSubscription(url);

        assertTrue(actualResult.isSuccess());
    }

    @Test
    public void whenCancellingSubscriptionThenResultShouldBeSuccessful() {
        final String url = "url";
        final String accountIdentifier = "accountIdentifier";
        Event event = new Event();
        Payload payload = new Payload();
        Account account = new Account(accountIdentifier);
        payload.setAccount(account);
        event.setPayload(payload);

        SuccessResult expectedResult = new SuccessResult();

        when(appDirectClient.getEvent(url, EventType.SUBSCRIPTION_CANCEL)).thenReturn(event);
        when(accountService.deleteAccount(accountIdentifier)).thenReturn(expectedResult);

        SuccessResult actualResult = (SuccessResult) classToTest.cancelSubscription(url);

        assertTrue(actualResult.isSuccess());
    }

    @Test
    public void wheProcessingSubscriptionNoticeWithClosedNoticeTypeThenAccountShouldBeDeletedAndResultShouldBeSuccessful() {
        final String url = "url";
        final String accountIdentifier = "accountIdentifier";
        Event event = new Event();
        Payload payload = new Payload();
        Account account = new Account(accountIdentifier);
        payload.setAccount(account);
        Notice notice = new Notice();
        notice.setType(NoticeType.CLOSED);
        payload.setNotice(notice);
        event.setPayload(payload);

        SuccessResult expectedResult = new SuccessResult();

        when(appDirectClient.getEvent(url, EventType.SUBSCRIPTION_NOTICE)).thenReturn(event);
        when(accountService.deleteAccount(accountIdentifier)).thenReturn(expectedResult);

        SuccessResult actualResult = (SuccessResult) classToTest.processSubscriptionNotice(url);

        verify(accountService, times(1)).deleteAccount(accountIdentifier);

        assertTrue(actualResult.isSuccess());
    }
}