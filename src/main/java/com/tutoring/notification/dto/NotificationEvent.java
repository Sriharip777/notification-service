package com.tutoring.notification.dto;

import lombok.Data;

@Data
public class NotificationEvent {

    private String userId;
    private String type;
    private String message;
    private String email;
}