package com.springapp.service;

import com.springapp.model.*;
import com.springapp.model.enums.ErrorCode;
import com.springapp.model.enums.EventFlag;
import com.springapp.model.enums.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mathieu on 6/25/2016.
 */
@Service
public class DefaultUserApiService implements UserApiService {
    private final AccountService accountService;
    private final AppDirectClient appDirectClient;

    @Autowired
    public DefaultUserApiService(AccountService accountService, AppDirectClient appDirectClient) {
        this.accountService = accountService;
        this.appDirectClient = appDirectClient;
    }

    @Override
    public Result assignUser(String url) {
        final Event event = appDirectClient.getEvent(url, EventType.USER_ASSIGNMENT);

        final Payload payload = event.getPayload();
        final Account account = payload.getAccount();
        final User user = payload.getUser();

        if (accountService.isUserWithUserUidPresent(user.getUuid())) {
            return new ErrorResult(ErrorCode.USER_ALREADY_EXISTS, String.format("User with %s uuid already exists.", user.getUuid()));
        }
        if (EventFlag.STATELESS.equals(event.getFlag())) {
            return new SuccessResult();
        }

        return accountService.createUser(user, account.getId());
    }

    @Override
    public Result unassignUser(String url) {
        final Event event = appDirectClient.getEvent(url, EventType.USER_UNASSIGNMENT);

        final Payload payload = event.getPayload();
        final Account account = payload.getAccount();
        final User user = payload.getUser();

        if (EventFlag.STATELESS.equals(event.getFlag())) {
            return new SuccessResult();
        }

        return accountService.deleteUser(user.getUuid());
    }
}
