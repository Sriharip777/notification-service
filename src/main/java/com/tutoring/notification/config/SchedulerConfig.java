package com.tutoring.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    // Enables @Scheduled jobs (retry, cleanup)
}
