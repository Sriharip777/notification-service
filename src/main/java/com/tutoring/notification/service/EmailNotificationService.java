package com.tutoring.notification.service;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.provider.EmailProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final EmailProvider emailProvider;
    private final TemplateService templateService;

    public void send(Notification notification) {


        NotificationTemplate template =
                templateService.getActiveTemplate(
                        notification.getTemplateCode(),
                        notification.getType()

                );

        // Simple rendering (we’ll improve later)
        String content = renderTemplate(
                template.getTemplatePath(),
                notification.getPayload()
        );

        emailProvider.sendEmail(
                notification.getRecipient(),
                template.getSubject(),
                content
        );
    }

    private String renderTemplate(String templatePath, Object payload) {
        // Step 7.2 → Thymeleaf rendering
        return "Email content rendered from template: " + templatePath;
    }
}