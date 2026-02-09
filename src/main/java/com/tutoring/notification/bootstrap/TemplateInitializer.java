package com.tutoring.notification.bootstrap;

import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.repository.NotificationTemplateRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemplateInitializer {

    private final NotificationTemplateRepository templateRepository;

    @PostConstruct
    public void initTemplates() {

        createIfNotExists(
                "WELCOME_EMAIL",
                NotificationType.EMAIL,
                "Welcome to EduConnect 🎉",
                "templates/email/welcome-email.html"
        );

        createIfNotExists(
                "BOOKING_CONFIRMATION",
                NotificationType.EMAIL,
                "Booking Confirmed ✅",
                "templates/email/booking-confirmation.html"
        );

        createIfNotExists(
                "CLASS_REMINDER",
                NotificationType.EMAIL,
                "Class Reminder ⏰",
                "templates/email/class-reminder.html"
        );

        createIfNotExists(
                "PAYMENT_RECEIPT",
                NotificationType.EMAIL,
                "Payment Received 💳",
                "templates/email/payment-receipt.html"
        );

        createIfNotExists(
                "REFUND_NOTIFICATION",
                NotificationType.EMAIL,
                "Refund Processed 💸",
                "templates/email/refund-notification.html"
        );

        createIfNotExists(
                "TEACHER_APPROVED",
                NotificationType.EMAIL,
                "Profile Approved 🎉",
                "templates/email/teacher-approved.html"
        );

        // ✅ FORGOT PASSWORD
        createIfNotExists(
                "FORGOT_PASSWORD",
                NotificationType.EMAIL,
                "Reset Your Password 🔐",
                "templates/email/forgot-password.html"
        );

        // =====================================================
        // ================= PUSH TEMPLATES ====================
        // =====================================================

        createIfNotExists(
                "CLASS_REMINDER",
                NotificationType.PUSH,
                "Class Reminder ⏰",
                null
        );

        createIfNotExists(
                "BOOKING_CONFIRMATION",
                NotificationType.PUSH,
                "Booking Confirmed ✅",
                null
        );

        createIfNotExists(
                "PAYMENT_RECEIPT",
                NotificationType.PUSH,
                "Payment Received 💳",
                null
        );

        createIfNotExists(
                "REFUND_NOTIFICATION",
                NotificationType.PUSH,
                "Refund Processed 💸",
                null
        );

        createIfNotExists(
                "TEACHER_APPROVED",
                NotificationType.PUSH,
                "Profile Approved 🎉",
                null
        );

        log.info("✅ Notification templates initialized");
    }

    private void createIfNotExists(
            String code,
            NotificationType type,
            String subject,
            String templatePath
    ) {
        templateRepository
                .findByCodeAndType(code, type)
                .ifPresentOrElse(
                        t -> log.info("ℹ️ Template already exists: {}", code),
                        () -> {
                            templateRepository.save(
                                    NotificationTemplate.builder()
                                            .code(code)
                                            .type(type)
                                            .subject(subject)
                                            .templatePath(templatePath)
                                            .active(true)
                                            .build()
                            );
                            log.info("🆕 Created template: {}", code);
                        }
                );
    }
}
