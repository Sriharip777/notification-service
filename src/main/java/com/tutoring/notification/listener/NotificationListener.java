package com.tutoring.notification.listener;

import com.tcon.events.events.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    @KafkaListener(
            topics = "notification-topic")
    public void listen(NotificationEvent event,
                       Acknowledgment acknowledgment) {

        try {
            log.info("📩 Received Notification Event");
            log.info("Email: {}", event.getEmail());
            log.info("Subject: {}", event.getSubject());
            log.info("Message: {}", event.getMessage());

            // TODO: call email sending logic here

            // ✅ manual commit (since enable-auto-commit = false)
            acknowledgment.acknowledge();

        } catch (Exception ex) {
            log.error("❌ Failed to process notification event", ex);
        }
    }
}