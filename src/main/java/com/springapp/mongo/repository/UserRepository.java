package com.springapp.mongo.repository;

import com.springapp.mongo.model.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Mathieu on 6/21/2016.
 */
public interface UserRepository extends MongoRepository<MongoUser, String> {
    MongoUser findByOpenId(String openId);
    MongoUser findByUuid(String uuid);
}
