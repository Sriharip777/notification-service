package com.tutoring.notification.event;

import com.tutoring.notification.dto.EmailNotificationRequest;
import com.tutoring.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentEventHandler {

    private final NotificationService notificationService;

    public void handle(Map<String, Object> eventData) {

        EmailNotificationRequest request = new EmailNotificationRequest();
        request.setTo((String) eventData.get("email"));
        request.setTemplateCode("payment-receipt");
        request.setPayload(eventData);

        notificationService.sendEmail(request);
    }
}
