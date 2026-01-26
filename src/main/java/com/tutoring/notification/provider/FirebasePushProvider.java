package com.tutoring.notification.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class FirebasePushProvider {

    public void sendPush(String deviceToken, Map<String, Object> payload) {

        // TEMP IMPLEMENTATION (until Firebase SDK is added)
        log.info(
                "📲 PUSH notification sent → deviceToken={}, payload={}",
                deviceToken,
                payload
        );

        // Later:
        // FirebaseMessaging.getInstance().send(...)
    }
}
