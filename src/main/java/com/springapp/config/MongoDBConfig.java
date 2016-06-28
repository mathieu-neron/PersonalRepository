package com.springapp.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Mathieu on 6/22/2016.
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.springapp.mongo.repository"})
public class MongoDBConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "storage";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new Mongo();
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.springapp.mongo";
    }
}
