package com.tutoring.notification.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final UserEventHandler userEventHandler;
    private final BookingEventHandler bookingEventHandler;
    private final PaymentEventHandler paymentEventHandler;
    private final SessionEventHandler sessionEventHandler;
    private final ReviewEventHandler reviewEventHandler;
    private final ReferralEventHandler referralEventHandler;

    /**
     * Central event dispatcher
     */
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

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported notification event type: " + eventType
                    );
        }
    }
}
