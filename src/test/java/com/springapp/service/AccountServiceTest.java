package com.springapp.service;

import com.springapp.model.builder.UserBuilder;
import com.springapp.model.Account;
import com.springapp.model.ErrorResult;
import com.springapp.model.Result;
import com.springapp.model.User;
import com.springapp.model.enums.ErrorCode;
import com.springapp.model.enums.SubscriptionStatus;
import com.springapp.mongo.model.MongoAccount;
import com.springapp.mongo.model.MongoUser;
import com.springapp.mongo.repository.AccountRepository;
import com.springapp.mongo.repository.UserRepository;
import com.springapp.service.validator.SubscriptionValidatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by Mathieu on 6/26/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    private AccountService classToTest;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SubscriptionValidatorService subscriptionValidatorService;

    @Before
    public void setUp() {
        classToTest = new AccountService(accountRepository,
                userRepository,
                subscriptionValidatorService);
    }

    @Test
    public void whenCreatingAnAccountThenResultShouldBeSuccessful() {
        User creator = new UserBuilder().withOpenId("openId").withUuid("uuid").withEmail("mathieu.neron@appdirect.com").withFirstName("Mathieu").withLastName("Neron").withLanguage("FR").build();

        Result result = classToTest.createAccount(creator);

        verify(userRepository, times(1)).save(any(MongoUser.class));
        verify(accountRepository, times(1)).save(any(MongoAccount.class));

        assertTrue(result.isSuccess());
    }

    @Test
    public void whenUpdatingAnExistingAccountThenResultShouldBeSuccessful() {
        final String accountId = "acountId";
        final String oldStatus = String.valueOf(SubscriptionStatus.ACTIVE);
        final String newStatus = String.valueOf(SubscriptionStatus.CANCELLED);
        final String edition = "new-edition";
        Account account = new Account(accountId);
        account.setStatus(newStatus);

        MongoAccount mongoAccount = new MongoAccount(oldStatus);

        when(accountRepository.findById(accountId)).thenReturn(mongoAccount);
        when(subscriptionValidatorService.validateSubscriptionStatusChange(SubscriptionStatus.ACTIVE, SubscriptionStatus.CANCELLED)).thenReturn(true);

        Result result = classToTest.updateAccount(account, edition);

        verify(accountRepository, times(1)).save(any(MongoAccount.class));

        assertTrue(result.isSuccess());
    }

    @Test
    public void whenUpdatingANonExistentAccountThenResultShouldFailWithAccountNotFoundErrorCode() {
        final String accountId = "acountId";
        final String status = "status";
        final String edition = "new-edition";
        Account account = new Account(accountId);
        account.setStatus(status);

        when(accountRepository.findById(accountId)).thenReturn(null);

        Result result = classToTest.updateAccount(account, edition);
        ErrorResult errorResult = (ErrorResult) result;

        verify(accountRepository, times(0)).save(any(MongoAccount.class));

        assertFalse(errorResult.isSuccess());
        assertThat(errorResult.getErrorCode(), is(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Test
    public void whenDeletingAnExistingAccountThenResultShouldBeSuccessful() {
        String accountId = "acountId";
        String userId = "userId";
        MongoUser mongoUser = new MongoUser();
        mongoUser.setUuid(userId);
        MongoAccount mongoAccount = new MongoAccount();
        mongoAccount.addUser(mongoUser);

        when(accountRepository.findById(accountId)).thenReturn(mongoAccount);
        when(userRepository.findByUuid(userId)).thenReturn(mongoUser);

        Result result = classToTest.deleteAccount(accountId);

        verify(userRepository, times(1)).delete(mongoUser);
        verify(accountRepository, times(1)).delete(mongoAccount);

        assertTrue(result.isSuccess());
    }

    @Test
    public void whenDeletingANonExistentAccountThenResultShouldFailWithAccountNotFoundErrorCode() {
        String accountId = "acountId";

        when(accountRepository.findById(accountId)).thenReturn(null);

        Result result = classToTest.deleteAccount(accountId);
        ErrorResult errorResult = (ErrorResult) result;

        verify(accountRepository, times(0)).delete(any(MongoAccount.class));

        assertFalse(errorResult.isSuccess());
        assertThat(errorResult.getErrorCode(), is(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Test
    public void whenCreatingAUserForAnExistingAccountThenResultShouldBeSuccessful() {
        User user = new UserBuilder().withOpenId("openId").withUuid("uuid").withEmail("mathieu.neron@appdirect.com").withFirstName("Mathieu").withLastName("Neron").withLanguage("FR").build();
        String accountId = "accountId";

        MongoAccount mongoAccount = new MongoAccount();

        when(accountRepository.findById(accountId)).thenReturn(mongoAccount);

        Result result = classToTest.createUser(user, accountId);

        verify(accountRepository, times(1)).save(mongoAccount);

        assertTrue(result.isSuccess());
    }

    @Test
    public void whenCreatingAUserForANonExistentAccountThenResultShouldFailWithAccountNotFoundErrorCode() {
        User user = new UserBuilder().withOpenId("openId").withUuid("uuid").withEmail("mathieu.neron@appdirect.com").withFirstName("Mathieu").withLastName("Neron").withLanguage("FR").build();
        String accountId = "accountId";

        when(accountRepository.findById(accountId)).thenReturn(null);

        Result result = classToTest.createUser(user, accountId);
        ErrorResult errorResult = (ErrorResult) result;

        verify(accountRepository, times(0)).save(any(MongoAccount.class));

        assertFalse(errorResult.isSuccess());
        assertThat(errorResult.getErrorCode(), is(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Test
    public void whenDeletingAUserForAnExistingAccountThenResultShouldBeSuccessful() {
        String userUid = "userUid";
        MongoUser mongoUser = new MongoUser();

        when(userRepository.findByUuid(userUid)).thenReturn(mongoUser);

        Result result = classToTest.deleteUser(userUid);

        verify(userRepository, times(1)).delete(mongoUser);

        assertTrue(result.isSuccess());
    }

    @Test
    public void whenDeletingAUserForANonExistentAccountThenResultShouldFailWithUserNotFoundErrorCode() {
        String userUid = "userUid";

        when(userRepository.findByUuid(userUid)).thenReturn(null);

        Result result = classToTest.deleteUser(userUid);
        ErrorResult errorResult = (ErrorResult) result;

        verify(userRepository, times(0)).delete(any(MongoUser.class));

        assertFalse(errorResult.isSuccess());
        assertThat(errorResult.getErrorCode(), is(ErrorCode.USER_NOT_FOUND));
    }
}