package com.tutoring.notification.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class FirebasePushProvider implements PushProvider {

    @Override
    public void sendPush(String token, String message) {
        System.out.println("Sending push to " + token);
    }
}
