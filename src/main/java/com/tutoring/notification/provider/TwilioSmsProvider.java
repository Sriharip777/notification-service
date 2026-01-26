package com.tutoring.notification.provider;

import org.springframework.stereotype.Component;

@Component
public class TwilioSmsProvider {

    public void sendSms(String phoneNumber, String message) {
        // Step 6.3.1 → Twilio integration (later)
        System.out.println("Sending SMS to: " + phoneNumber);
    }
}
