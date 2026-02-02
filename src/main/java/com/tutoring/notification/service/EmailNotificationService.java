package com.tutoring.notification.service;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender mailSender;
    private final NotificationRepository notificationRepository;

    public void send(Notification notification) {

        log.info("📧 Sending email to {}", notification.getRecipient());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getRecipient());
            message.setSubject("Notification: " + notification.getTemplateCode());
            message.setText(notification.getPayload().toString());

            mailSender.send(message);

            // ✅ SUCCESS
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            notification.setFailureReason(null);

            log.info("✅ Email sent successfully to {}", notification.getRecipient());

        } catch (Exception e) {
            // ❌ FAILURE
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(e.getMessage());
            notification.setRetryCount(notification.getRetryCount() + 1);

            log.error("❌ Email sending failed for {} : {}", notification.getRecipient(), e.getMessage(), e);
        }

        // 🔥 Save final status to MongoDB
        notificationRepository.save(notification);
    }
}
