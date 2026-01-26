package com.tutoring.notification.service;

import com.tutoring.notification.dto.EmailNotificationRequest;
import com.tutoring.notification.dto.PushNotificationRequest;
import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

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
            switch (type) {
                case EMAIL -> emailService.send(notification);
                case PUSH  -> pushService.send(notification);

            }

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(ex.getMessage());
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

        // ✅ HARD STOP
        if (notification.getRetryCount() >= 3) {
            log.warn("Max retry limit reached for notification {}", notification.getId());
            return;
        }

        try {
            notification.setRetryCount(notification.getRetryCount() + 1);
            notification.setStatus(NotificationStatus.RETRYING);
            notificationRepository.save(notification);

            switch (notification.getType()) {
                case EMAIL -> emailService.send(notification);
                case PUSH  -> pushService.send(notification);

            }

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(ex.getMessage());
        }

        notificationRepository.save(notification);
    }


    // ================= HISTORY / ADMIN =================

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getById(String id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
    }

    public List<Notification> getByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

}
