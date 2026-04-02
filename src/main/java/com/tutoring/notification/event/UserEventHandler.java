package com.tutoring.notification.event;

import com.tutoring.notification.dto.EmailNotificationRequest;
import com.tutoring.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserEventHandler {

    private final NotificationService notificationService;

    /**
     * Handles USER_CREATED event
     */
    public void handle(Map<String, Object> eventData) {

        EmailNotificationRequest request =
                EmailNotificationRequest.builder()
                        .to((String) eventData.get("email"))
                        .templateCode("user-welcome")
                        .payload(eventData)
                        .build();

        notificationService.sendEmail(request);
    }
}
