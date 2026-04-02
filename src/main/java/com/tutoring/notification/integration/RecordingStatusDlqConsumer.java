package com.tutoring.notification.integration.dlq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutoring.notification.integration.RecordingStatusConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecordingStatusDlqConsumer {

    private final RecordingStatusConsumer mainConsumer;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "notification-topic.DLT",
            groupId = "notification-dlq-group"
    )
    public void consume(String message) throws Exception {

        log.warn("♻️ Replaying message from DLQ: {}", message);

        Map<String, Object> event =
                objectMapper.readValue(
                        message,
                        new TypeReference<Map<String, Object>>() {}
                );

        mainConsumer.consume(event);
    }
}
