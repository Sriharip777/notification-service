package com.tutoring.notification.dto;

import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.entity.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class NotificationDto {

    private String id;

    private String userId;

    private NotificationType type;

    private NotificationStatus status;

    private String recipient;

    private Map<String, Object> payload;

    private LocalDateTime sentAt;

    private String failureReason;
}
