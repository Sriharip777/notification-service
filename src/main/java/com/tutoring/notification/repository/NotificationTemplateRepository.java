package com.tutoring.notification.repository;

import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.entity.NotificationType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationTemplateRepository
        extends MongoRepository<NotificationTemplate, String> {

    // Used by TemplateInitializer (startup seeding)
    Optional<NotificationTemplate> findByCodeAndType(
            String code,
            NotificationType type
    );

    // Used by TemplateService (runtime lookup)
    Optional<NotificationTemplate> findByCodeAndTypeAndActiveTrue(
            String code,
            NotificationType type
    );
}
