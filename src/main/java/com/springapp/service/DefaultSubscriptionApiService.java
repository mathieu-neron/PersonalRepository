package com.springapp.service;

import com.springapp.model.*;
import com.springapp.model.enums.ErrorCode;
import com.springapp.model.enums.EventFlag;
import com.springapp.model.enums.EventType;
import com.springapp.model.enums.NoticeType;
import com.springapp.service.validator.SubscriptionValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mathieu on 6/25/2016.
 */
@Service
public class DefaultSubscriptionApiService implements SubscriptionApiService {
    private final AccountService accountService;
    private final AppDirectClient appDirectClient;

    @Autowired
    public DefaultSubscriptionApiService(AccountService accountService, AppDirectClient appDirectClient) {
        this.accountService = accountService;
        this.appDirectClient = appDirectClient;
    }

    @Override
    public Result createSubscription(String url) {
        final Event event = appDirectClient.getEvent(url, EventType.SUBSCRIPTION_ORDER);
        final User creator = event.getCreator();

        if (accountService.isUserWithUserUidPresent(creator.getUuid())) {
            return new ErrorResult(ErrorCode.USER_ALREADY_EXISTS, String.format("User with %s uuid already exists.", creator.getUuid()));
        }
        if (EventFlag.STATELESS.equals(event.getFlag())) {
            return new SuccessResult();
        }

        return accountService.createAccount(creator);
    }

    @Override
    public Result changeSubscription(String url) {
        Event event = appDirectClient.getEvent(url, EventType.SUBSCRIPTION_CHANGE);

        final Payload payload = event.getPayload();
        final Account account = payload.getAccount();
        final Order order = payload.getOrder();
        final String edition = order.getEditionCode();

        if (EventFlag.STATELESS.equals(event.getFlag())) {
            return new SuccessResult();
        }

        return accountService.updateAccount(account, edition);
    }

    @Override
    public Result cancelSubscription(String url) {
        final Event event = appDirectClient.getEvent(url, EventType.SUBSCRIPTION_CANCEL);

        final Payload payload = event.getPayload();
        final Account account = payload.getAccount();

        if (EventFlag.STATELESS.equals(event.getFlag())) {
            return new SuccessResult();
        }

        return accountService.deleteAccount(account.getId());
    }

    @Override
    public Result processSubscriptionNotice(String url) {
        Event event = appDirectClient.getEvent(url, EventType.SUBSCRIPTION_NOTICE);

        final Payload payload = event.getPayload();
        final Notice notice = payload.getNotice();
        final NoticeType noticeType = notice.getType();

        if (EventFlag.STATELESS.equals(event.getFlag())) {
            return new SuccessResult();
        }

        if (noticeType.equals(NoticeType.CLOSED)) {
            return accountService.deleteAccount(payload.getAccount().getId());
        }

        return new SuccessResult();
    }
}
