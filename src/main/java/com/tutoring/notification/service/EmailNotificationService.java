package com.tutoring.notification.service;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.provider.EmailProvider;
import com.tutoring.notification.repository.NotificationRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final EmailProvider emailProvider;
    private final TemplateService templateService;
    private final EmailTemplateLoader templateLoader;
    private final TemplateRenderer renderer;
    private final NotificationRepository notificationRepository;

    /**
     * 🔁 Retry up to 3 times with exponential backoff
     * 1st retry → 2s
     * 2nd retry → 4s
     *
     * 🔥 Wrapped with Circuit Breaker
     */
    @Retryable(
            value = { Exception.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @CircuitBreaker(
            name = "emailService",
            fallbackMethod = "circuitBreakerFallback"
    )
    public void send(Notification notification) {

        log.info(
                "📧 [EMAIL] Preparing to send | notificationId={} | to={}",
                notification.getId(),
                notification.getRecipient()
        );

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
    }

    /**
     * 🚨 Called when all retry attempts fail
     */
    @Recover
    public void recover(Exception ex, Notification notification) {

        log.error(
                "🚨 Email permanently FAILED after retries | notificationId={}",
                notification.getId(),
                ex
        );

        notification.setStatus(NotificationStatus.FAILED);
        notification.setFailureReason(
                "Permanent failure after retries: " + ex.getMessage()
        );

        notificationRepository.save(notification);
    }

    /**
     * 🔥 Circuit Breaker fallback
     * Triggered when circuit is OPEN
     */
    public void circuitBreakerFallback(
            Notification notification,
            Throwable ex) {

        log.error(
                "🚨 Circuit Breaker OPEN — Email blocked | notificationId={}",
                notification.getId(),
                ex
        );

        notification.setStatus(NotificationStatus.FAILED);
        notification.setFailureReason(
                "Circuit breaker open: " + ex.getMessage()
        );

        notificationRepository.save(notification);
    }
}
