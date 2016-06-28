package com.springapp.service;

import com.springapp.model.*;
import com.springapp.model.enums.ErrorCode;
import com.springapp.model.enums.SubscriptionStatus;
import com.springapp.mongo.model.MongoAccount;
import com.springapp.mongo.model.MongoUser;
import com.springapp.mongo.repository.AccountRepository;
import com.springapp.mongo.repository.UserRepository;
import com.springapp.service.validator.SubscriptionValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * Created by Mathieu on 6/22/2016.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final SubscriptionValidatorService subscriptionValidatorService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository,
                          SubscriptionValidatorService subscriptionValidatorService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.subscriptionValidatorService = subscriptionValidatorService;
    }

    @Transactional
    public Result createAccount(User creator) {
        MongoUser mongoCreator = creator.toMongoUser();
        userRepository.save(mongoCreator);

        MongoAccount mongoAccount = new MongoAccount();
        mongoAccount.setUsers(Collections.singletonList(mongoCreator));
        accountRepository.save(mongoAccount);
        return new SuccessResult(mongoAccount.getId());
    }

    @Transactional
    public Result updateAccount(Account account, String edition) {
        final String accountId = account.getId();
        Optional<MongoAccount> mongoAccountOptional = findAccountById(accountId);
        if (!mongoAccountOptional.isPresent()) {
            return new ErrorResult(ErrorCode.ACCOUNT_NOT_FOUND, String.format("Account with %s accountIdentifier not found.", accountId));
        }

        MongoAccount mongoAccount = mongoAccountOptional.get();

        if (!subscriptionValidatorService
                .validateSubscriptionStatusChange(SubscriptionStatus.valueOf(mongoAccount.getStatus()),
                        SubscriptionStatus.valueOf(account.getStatus()))) {
            return new ErrorResult(ErrorCode.UNAUTHORIZED,
                    String.format("Changing subscription status from %s to %s is unauthorized.",
                            mongoAccount.getStatus(),
                            account.getStatus()));

        }

        mongoAccount.setEdition(edition);
        mongoAccount.setStatus(account.getStatus());
        accountRepository.save(mongoAccount);
        return new SuccessResult();
    }

    @Transactional
    public Result deleteAccount(String accountId) {
        Optional<MongoAccount> mongoAccountOptional = findAccountById(accountId);
        if (mongoAccountOptional.isPresent()) {
            MongoAccount mongoAccount = mongoAccountOptional.get();
            MongoUser mongoUser = mongoAccount.getUsers().get(0);

            Result result = deleteUser(mongoUser.getUuid());

            if (!result.isSuccess()) {
                return result;
            }

            accountRepository.delete(mongoAccountOptional.get());
            return new SuccessResult();
        }
        return new ErrorResult(ErrorCode.ACCOUNT_NOT_FOUND, String.format("Account with %s accountIdentifier not found.", accountId));
    }

    @Transactional
    public Result createUser(User user, String accountId) {
        Optional<MongoAccount> mongoAccountOptional = findAccountById(accountId);

        if (!mongoAccountOptional.isPresent()) {
            return new ErrorResult(ErrorCode.ACCOUNT_NOT_FOUND, String.format("Account with %s accountIdentifier not found.", accountId));
        }

        MongoAccount mongoAccount = mongoAccountOptional.get();

        MongoUser mongoUser  = user.toMongoUser();
        mongoAccount.addUser(mongoUser);
        accountRepository.save(mongoAccount);

        return new SuccessResult();
    }

    @Transactional
    public Result deleteUser(String userUid) {
        Optional<MongoUser> user = findUserByUserUid(userUid);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return new SuccessResult();
        }
        return new ErrorResult(ErrorCode.USER_NOT_FOUND, String.format("User with %s userUid not found.", userUid));
    }

    public Boolean isUserWithUserUidPresent(String userUid) {
        return findUserByUserUid(userUid).isPresent();
    }

    private Optional<MongoAccount> findAccountById(String accountId) {
        Optional<MongoAccount> account = Optional.ofNullable(accountRepository.findById(accountId));
        return account;
    }

    private Optional<MongoUser> findUserByUserUid(String userUid) {
        Optional<MongoUser> user = Optional.ofNullable(userRepository.findByUuid(userUid));
        return user;
    }

    private Optional<MongoUser> findUserByOpenId(String openId) {
        Optional<MongoUser> user = Optional.ofNullable(userRepository.findByOpenId(openId));
        return user;
    }
}
