package com.springapp.mongo.repository;

import com.springapp.mongo.model.MongoAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Mathieu on 6/21/2016.
 */
public interface AccountRepository extends MongoRepository<MongoAccount, String> {
    MongoAccount findByAccountIdentifier(String accountIdentifier);
}
