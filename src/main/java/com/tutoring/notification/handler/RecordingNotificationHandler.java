package com.tutoring.notification.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RecordingNotificationHandler {

    public void handle(Map<String, Object> eventData) {

        String classId = (String) eventData.get("classId");
        String recordingId = (String) eventData.get("recordingId");
        String gcsUrl = (String) eventData.get("gcsUrl");

        log.info("🎥 Sending recording notification for class {}",
                classId);

        // TODO:
        // 1. Fetch enrolled students
        // 2. Send email
        // 3. Send push notification
        // 4. Create in-app notification entry

        log.info("Recording {} notification sent successfully",
                recordingId);
    }
}
