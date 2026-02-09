package com.tutoring.notification.service;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.provider.EmailProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final EmailProvider emailProvider;
    private final TemplateService templateService;
    private final EmailTemplateLoader templateLoader;
    private final TemplateRenderer renderer;

    public void send(Notification notification) {

        log.info(
                "📧 [EMAIL] Preparing to send | notificationId={} | to={}",
                notification.getId(),
                notification.getRecipient()
        );

        try {
            // 1️⃣ Validate recipient
            if (notification.getRecipient() == null || notification.getRecipient().isBlank()) {
                throw new IllegalArgumentException("Recipient email is missing");
            }

            // 2️⃣ Fetch active template
            NotificationTemplate template =
                    templateService.getActiveTemplate(
                            notification.getTemplateCode(),
                            notification.getType()
                    );

            log.info(
                    "📄 [EMAIL] Using template | code={} | subject={}",
                    template.getCode(),
                    template.getSubject()
            );

            // 3️⃣ Load HTML template
            String htmlTemplate =
                    templateLoader.loadTemplate(template.getTemplatePath());

            // 4️⃣ Render template with payload
            String finalHtml =
                    renderer.render(htmlTemplate, notification.getPayload());

            // 5️⃣ Send email
            emailProvider.sendEmail(
                    notification.getRecipient(),
                    template.getSubject(),
                    finalHtml
            );

            log.info(
                    "✅ [EMAIL] Sent successfully | notificationId={} | to={}",
                    notification.getId(),
                    notification.getRecipient()
            );

        } catch (Exception ex) {

            log.error(
                    "❌ [EMAIL] Sending FAILED | notificationId={} | to={}",
                    notification.getId(),
                    notification.getRecipient(),
                    ex
            );

            // IMPORTANT: rethrow so NotificationService marks FAILED + retry works
            throw ex;
        }
    }
}
