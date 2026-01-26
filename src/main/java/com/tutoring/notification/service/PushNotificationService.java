package com.tutoring.notification.service;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.provider.FirebasePushProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PushNotificationService {

    private final FirebasePushProvider pushProvider;
    private final TemplateService templateService;

    public void send(Notification notification) {

        // 1️⃣ Load template
        NotificationTemplate template =
                templateService.getActiveTemplate(
                        notification.getTemplateCode(),
                        notification.getType()
                );

        // 2️⃣ Render payload
        Map<String, Object> renderedPayload =
                renderPayload(template.getTemplatePath(), notification.getPayload());

        // 3️⃣ Send push
        pushProvider.sendPush(
                notification.getRecipient(), // deviceToken
                renderedPayload
        );
    }

    /**
     * Simple payload renderer
     * (later can be JSON / Freemarker / Thymeleaf)
     */
    private Map<String, Object> renderPayload(
            String templatePath,
            Map<String, Object> payload
    ) {
        // For now → return payload as-is
        return payload;
    }
}
