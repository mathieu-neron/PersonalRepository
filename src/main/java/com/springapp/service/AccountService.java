package com.springapp.service;

import com.springapp.model.Account;
import com.springapp.model.User;
import com.springapp.model.dto.MongoAccount;
import com.springapp.model.dto.MongoUser;
import com.springapp.repository.AccountRepository;
import com.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Mathieu on 6/22/2016.
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createAccount(Account account, User creator) {
        MongoUser mongoCreator = creator.toMongoUser();
        userRepository.save(mongoCreator);
        creator.setId(mongoCreator.getId());

        MongoAccount mongoAccount = account.toMongoAccount();
        accountRepository.save(mongoAccount);
        account.setId(account.getId());
    }

    @Transactional(readOnly = true)
    public List<Account> getAccounts() {
        List<MongoAccount> mongoAccounts = accountRepository.findAll();
        return mongoAccounts.stream()
                .map(Account::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Account getAccount(String accountId) {
        Optional<MongoAccount> account = findAccountById(accountId);
        return new Account(account.get());
    }

    @Transactional
    public Boolean deleteAccount(String accountId) {
        Optional<MongoAccount> mongoAccountOptional = findAccountById(accountId);
        if (mongoAccountOptional.isPresent()) {
            accountRepository.delete(mongoAccountOptional.get());
            return true;
        }

        return false;
    }

    @Transactional
    public Boolean createUser(User user, String accountId) {
        Optional<MongoAccount> mongoAccountOptional = findAccountById(accountId);

        if (!mongoAccountOptional.isPresent()) {
            return false;
        }

        MongoAccount mongoAccount = mongoAccountOptional.get();

        MongoUser mongoUser  = user.toMongoUser();
        mongoAccount.addUser(mongoUser);
        accountRepository.save(mongoAccount);
        user.setId(mongoUser.getId());

        return true;
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        List<MongoUser> mongoUsers = userRepository.findAll();
        return mongoUsers.stream()
                .map(User::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Boolean deleteUser(String userId) {
        Optional<MongoUser> user = findUserById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    private Optional<MongoAccount> findAccountById(String accountId) {
        Optional<MongoAccount> account = Optional.of(accountRepository.findOne(accountId));
        return account;
    }

    private Optional<MongoUser> findUserById(String userId) {
        Optional<MongoUser> user = Optional.of(userRepository.findOne(userId));
        return user;
    }
}
