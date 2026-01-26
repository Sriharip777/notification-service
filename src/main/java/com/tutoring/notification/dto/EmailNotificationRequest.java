package com.tutoring.notification.dto;

import lombok.Data;
import java.util.Map;

@Data
public class EmailNotificationRequest {

    private String to;
    private String templateCode;
    private Map<String, Object> payload;
}
