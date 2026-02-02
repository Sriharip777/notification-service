package com.tutoring.notification.dto;

import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.entity.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationHistoryDto {

    private String id;

    private NotificationType type;

    private NotificationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime sentAt;
}
