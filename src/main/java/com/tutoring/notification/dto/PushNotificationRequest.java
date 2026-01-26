package com.tutoring.notification.dto;

import lombok.Data;
import java.util.Map;

@Data
public class PushNotificationRequest {

    private String deviceToken;
    private String templateCode;
    private Map<String, Object> payload;
}
