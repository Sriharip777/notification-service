package com.tutoring.notification.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmailNotificationRequest {

    private String to;
    private String templateCode;
    private Map<String, Object> payload;
}
