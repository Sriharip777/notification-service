package com.tutoring.notification.service;

import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.repository.NotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final NotificationTemplateRepository templateRepository;

    public NotificationTemplate getActiveTemplate(
            String code,
            NotificationType type
    ) {
        return templateRepository
                .findByCodeAndTypeAndActiveTrue(code, type)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active template not found: " + code
                        ));
    }
}
