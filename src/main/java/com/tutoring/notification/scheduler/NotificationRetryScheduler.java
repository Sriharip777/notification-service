package com.tutoring.notification.scheduler;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.repository.NotificationRepository;
import com.tutoring.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRetryScheduler {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    // Runs every 2 minutes
    @Scheduled(fixedDelay = 120000)
    public void retryFailedNotifications() {

        List<Notification> failedNotifications =
                notificationRepository.findByStatusAndRetryCountLessThan(
                        NotificationStatus.FAILED,
                        3
                );

        if (failedNotifications.isEmpty()) {
            return;
        }

        log.info("🔁 Retrying {} failed notifications", failedNotifications.size());

        for (Notification notification : failedNotifications) {
            notificationService.retry(notification);
        }
    }
}
