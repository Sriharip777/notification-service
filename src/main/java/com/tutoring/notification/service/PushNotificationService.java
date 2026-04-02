package com.tutoring.notification.service;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.provider.PushProvider;
import com.tutoring.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService {

    private final PushProvider pushProvider;
    private final TemplateService templateService;
    private final NotificationRepository notificationRepository;

    public void send(Notification notification) {

        log.info(
                "📲 [PUSH] Preparing to send | notificationId={} | to={}",
                notification.getId(),
                notification.getRecipient()
        );

        if (notification.getRecipient() == null || notification.getRecipient().isBlank()) {
            throw new IllegalArgumentException("Device token is missing");
        }

        NotificationTemplate template =
                templateService.getActiveTemplate(
                        notification.getTemplateCode(),
                        notification.getType()
                );

        if (template == null) {
            throw new RuntimeException("Push template not found");
        }

        pushProvider.sendPush(
                notification.getRecipient(),
                template.getSubject()
        );

        log.info(
                "✅ [PUSH] Sent successfully | notificationId={} | to={}",
                notification.getId(),
                notification.getRecipient()
        );
    }
}