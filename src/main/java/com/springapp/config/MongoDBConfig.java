package com.springapp.config;

import com.mongodb.*;
import com.springapp.mongo.cascade.CascadeSaveMongoEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 6/22/2016.
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.springapp.mongo.repository"})
public class MongoDBConfig extends AbstractMongoConfiguration {

    private String mongoUserName = "test";
    private String mongoPassword = "test";

//    @Value("${spring.data.mongodb.uri}")
    private String mongoURI = "mongodb://test:test@ds023654.mlab.com:23654/heroku_1qjc7193";

//    @Value("${spring.data.mongodb.database}")
    private String mongoDB = "heroku_1qjc7193";

    @Override
    protected String getDatabaseName() {
        return "heroku_1qjc7193";
    }

    @Override
    public Mongo mongo() throws Exception {

//        List<ServerAddress> seeds = new ArrayList<>();
//        seeds.add( new ServerAddress( "ds023654.mlab.com", 23654));
//        List<MongoCredential> credentials = new ArrayList<>();
//        credentials.add(
//                MongoCredential.createScramSha1Credential(
//                        mongoUserName,
//                        mongoDB,
//                        mongoPassword.toCharArray()
//                )
//        );

        MongoClientURI mongoClientURI = new MongoClientURI(mongoURI);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        return mongoClient;
    }

    @Override
    protected String getMappingBasePackage() {
        return mongoDB;
    }

    @Bean
    public CascadeSaveMongoEventListener userCascadingMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }
}
