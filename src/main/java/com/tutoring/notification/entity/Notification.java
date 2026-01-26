package com.tutoring.notification.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private String id;

    private String userId;

    private NotificationType type;

    private NotificationStatus status;

    private String templateCode;

    /**
     * Dynamic values used in template rendering
     * Example: { "userName": "Manoj", "classTime": "10:30 AM" }
     */
    private Map<String, Object> payload;

    private String recipient; // email / phone / deviceToken

    private int retryCount;

    private LocalDateTime scheduledAt;

    private LocalDateTime sentAt;

    private String failureReason;

    private LocalDateTime createdAt;
}
