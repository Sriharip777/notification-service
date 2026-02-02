package com.tutoring.notification.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GmailSmtpProvider implements EmailProvider {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
