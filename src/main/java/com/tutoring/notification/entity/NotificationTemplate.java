package com.tutoring.notification.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification_templates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate {

    @Id
    private String id;

    private String code; // e.g. WELCOME_EMAIL, BOOKING_CONFIRMATION

    private NotificationType type;

    private String subject; // email only

    private String templatePath;
    // example: templates/email/welcome-email.html

    private boolean active;
}
