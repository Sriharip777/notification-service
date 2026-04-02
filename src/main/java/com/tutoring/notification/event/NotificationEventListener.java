package com.tutoring.notification.event;

import com.tutoring.notification.handler.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final UserEventHandler userEventHandler;
    private final BookingEventHandler bookingEventHandler;
    private final PaymentEventHandler paymentEventHandler;
    private final SessionEventHandler sessionEventHandler;
    private final ReviewEventHandler reviewEventHandler;
    private final ReferralEventHandler referralEventHandler;
    private final RecordingNotificationHandler recordingNotificationHandler;

    @KafkaListener(
            topics = "notification-topic",
            groupId = "notification-group"
    )
    public void consume(Map<String, Object> event) {

        String eventType = (String) event.get("eventType");
        Map<String, Object> eventData =
                (Map<String, Object>) event.get("eventData");

        log.info("🔔 Received notification event: {}", eventType);

        handle(eventType, eventData);
    }

    public void handle(String eventType, Map<String, Object> eventData) {

        switch (eventType) {

            case "USER_CREATED" ->
                    userEventHandler.handle(eventData);

            case "BOOKING_CONFIRMED" ->
                    bookingEventHandler.handle(eventData);

            case "PAYMENT_SUCCESS" ->
                    paymentEventHandler.handle(eventData);

            case "SESSION_REMINDER" ->
                    sessionEventHandler.handle(eventData);

            case "REVIEW_REQUEST" ->
                    reviewEventHandler.handle(eventData);

            case "REFERRAL_INVITE" ->
                    referralEventHandler.handle(eventData);

            case "RECORDING_AVAILABLE" ->
                    recordingNotificationHandler.handle(eventData);

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported notification event type: " + eventType
                    );
        }
    }
}
