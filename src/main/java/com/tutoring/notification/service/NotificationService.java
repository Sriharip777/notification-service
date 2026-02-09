package com.tutoring.notification.service;

import com.tutoring.notification.dto.EmailNotificationRequest;
import com.tutoring.notification.dto.PushNotificationRequest;
import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailNotificationService emailService;
    private final PushNotificationService pushService;

    // ================= CORE SEND METHOD =================
    public Notification sendNotification(
            String userId,
            NotificationType type,
            String recipient,
            String templateCode,
            Map<String, Object> payload
    ) {

        Notification notification = Notification.builder()
                .userId(userId)
                .type(type)
                .recipient(recipient)
                .templateCode(templateCode)
                .payload(payload)
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .build();

        notification = notificationRepository.save(notification);

        try {
            log.info("📨 Sending {} notification to {}", type, recipient);

            switch (type) {
                case EMAIL -> emailService.send(notification);
                case PUSH  -> pushService.send(notification);
            }

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

            log.info("✅ Notification SENT. Type={}, Recipient={}", type, recipient);

        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(ex.getMessage());

            log.error(
                    "❌ Notification send FAILED. Type={}, Recipient={}",
                    type,
                    recipient,
                    ex
            );
        }

        return notificationRepository.save(notification);
    }

    // ================= EMAIL =================
    public Notification sendEmail(EmailNotificationRequest request) {
        return sendNotification(
                null,
                NotificationType.EMAIL,
                request.getTo(),
                request.getTemplateCode(),
                request.getPayload()
        );
    }

    // ================= PUSH =================
    public Notification sendPush(PushNotificationRequest request) {
        return sendNotification(
                null,
                NotificationType.PUSH,
                request.getDeviceToken(),
                request.getTemplateCode(),
                request.getPayload()
        );
    }

    // ================= RETRY LOGIC (USED BY SCHEDULER) =================
    public void retry(Notification notification) {

        if (notification.getRetryCount() >= 3) {
            log.warn(
                    "⛔ Max retry limit reached for notification {}",
                    notification.getId()
            );
            return;
        }

        try {
            log.info(
                    "🔁 Retrying notification {} (attempt {})",
                    notification.getId(),
                    notification.getRetryCount() + 1
            );

            switch (notification.getType()) {
                case EMAIL -> emailService.send(notification);
                case PUSH  -> pushService.send(notification);
            }

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

            log.info(
                    "✅ Retry SUCCESS for notification {}",
                    notification.getId()
            );

        } catch (Exception ex) {
            notification.setRetryCount(notification.getRetryCount() + 1);
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(ex.getMessage());

            log.error(
                    "❌ Retry FAILED for notification {} (attempt {})",
                    notification.getId(),
                    notification.getRetryCount(),
                    ex
            );
        }

        notificationRepository.save(notification);
    }

    // ================= HISTORY / ADMIN =================
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getById(String id) {
        return notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found: " + id)
                );
    }

    public List<Notification> getByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }
}
