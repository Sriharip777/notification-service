package com.tutoring.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class DatabaseConfig {
    // Enables @CreatedDate and @LastModifiedDate in Mongo entities
}
