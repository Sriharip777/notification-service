package com.tutoring.notification.service;

import com.tutoring.notification.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsNotificationService {

    public void send(Notification notification) {
        // STUB IMPLEMENTATION (NO REAL PROVIDER)
        log.info(
                "Sending SMS to {} using template {} with payload {}",
                notification.getRecipient(),
                notification.getTemplateCode(),
                notification.getPayload()
        );

        // Simulate success
        // (later Twilio / provider can be plugged here)
    }
}
