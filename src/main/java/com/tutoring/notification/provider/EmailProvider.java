package com.tutoring.notification.provider;

public interface EmailProvider {

    void sendEmail(String to, String subject, String content);
}
