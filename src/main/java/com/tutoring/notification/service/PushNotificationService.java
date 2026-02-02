package com.tutoring.notification.service;

import com.tutoring.notification.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushNotificationService {

    public void send(Notification notification) {

        // Firebase is disabled → mock push
        log.info("📲 Mock push sent to device token {}", notification.getRecipient());
        log.info("Payload: {}", notification.getPayload());
    }
}