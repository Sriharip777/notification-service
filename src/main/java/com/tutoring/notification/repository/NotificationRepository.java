package com.tutoring.notification.repository;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository
        extends MongoRepository<Notification, String> {

    List<Notification> findByStatus(NotificationStatus status);

    // ✅ USED BY SCHEDULER
    List<Notification> findByStatusAndRetryCountLessThan(
            NotificationStatus status,
            int retryCount
    );
}
