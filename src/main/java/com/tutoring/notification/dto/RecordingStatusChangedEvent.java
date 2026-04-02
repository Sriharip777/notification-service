package com.tutoring.notification.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class RecordingStatusChangedEvent {

    private String eventId;      // ✅ NEW
    private String recordingId;
    private String status;
    private Instant changedAt;
    private String studentEmail;
}
