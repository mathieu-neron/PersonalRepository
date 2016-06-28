package com.springapp.mongo.repository;

import com.springapp.mongo.model.MongoAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Mathieu on 6/21/2016.
 */
@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface AccountRepository extends MongoRepository<MongoAccount, String> {
    MongoAccount findById(String id);
}
