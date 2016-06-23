package com.springapp.repository;

import com.springapp.model.dto.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Mathieu on 6/21/2016.
 */
public interface UserRepository extends MongoRepository<MongoUser, String> {
    List<MongoUser> findByOpenId(String openId);
    List<MongoUser> findByUuid(String uuid);
}
