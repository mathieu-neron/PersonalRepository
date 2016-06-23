package com.springapp.repository;

import com.springapp.model.dto.MongoAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Mathieu on 6/21/2016.
 */
public interface AccountRepository extends MongoRepository<MongoAccount, String> {
    public MongoAccount findByAccountIdentifier(String accountIdentifier);
}
