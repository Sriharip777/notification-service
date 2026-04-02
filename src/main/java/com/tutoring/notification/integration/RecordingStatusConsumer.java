package com.tutoring.notification.integration;

import com.tutoring.notification.entity.ProcessedEvent;
import com.tutoring.notification.repository.ProcessedEventRepository;
import com.tutoring.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecordingStatusConsumer {

    private final NotificationService notificationService;
    private final ProcessedEventRepository processedEventRepository;

    @KafkaListener(
            topics = "notification-topic",
            groupId = "notification-service-group"
    )
    public void consume(Map<String, Object> event) {

        log.info("📩 Notification event received: {}", event);

        try {

            String eventId = (String) event.get("eventId");
            String eventType = (String) event.get("eventType");
            Map<String, Object> eventData =
                    (Map<String, Object>) event.get("eventData");

            // 🔐 STEP 1 — Idempotency Check
            if (processedEventRepository.findByEventId(eventId).isPresent()) {
                log.warn("⚠️ Duplicate event detected. Skipping eventId={}", eventId);
                return;
            }

            // 🔥 STEP 2 — Handle Recording Available Event
            if ("RECORDING_AVAILABLE".equals(eventType)) {

                String recordingId = (String) eventData.get("recordingId");
                String classId = (String) eventData.get("classId");
                String gcsUrl = (String) eventData.get("gcsUrl");

                log.info("🎯 Handling RECORDING_AVAILABLE for recordingId={}", recordingId);

                notificationService.sendRecordingReadyNotification(
                        recordingId,
                        classId,
                        gcsUrl
                );
            }

            // 🔐 STEP 3 — Mark As Processed
            processedEventRepository.save(
                    ProcessedEvent.builder()
                            .eventId(eventId)
                            .processedAt(Instant.now())
                            .build()
            );

            log.info("✅ Event processed successfully. eventId={}", eventId);

        } catch (Exception e) {
            log.error("❌ Error processing notification event", e);
            throw e; // Important: allows retry + DLT
        }
    }
}
