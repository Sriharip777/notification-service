package com.tutoring.notification.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * Firebase configuration is TEMPORARILY DISABLED.
 *
 * Reason:
 * - firebase-service-account.json is not added yet
 * - Push notifications are not required right now
 *
 * This will NOT affect:
 * - Email notifications
 * - MongoDB
 * - REST APIs
 * - Application startup
 *
 * Firebase can be safely enabled later.
 */
@Slf4j
@Configuration
public class FirebaseConfig {

    // 🔕 Firebase disabled intentionally
    // Enable later when push notifications are required

}
