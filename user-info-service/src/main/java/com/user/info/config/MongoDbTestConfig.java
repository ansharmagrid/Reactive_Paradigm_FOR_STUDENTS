package com.user.info.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@TestConfiguration
@Profile("test")
public class MongoDbTestConfig {

	@Bean
    MongoClient mongoClient() {
        String connectionString = "mongodb://localhost:27027";
        ConnectionString connString = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                                    .applyConnectionString(connString)
                                    .build();
        return MongoClients.create(settings);
    }
	
}
