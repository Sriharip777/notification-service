package com.tutoring.notification.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class EmailTemplateLoader {

    /**
     * Loads HTML email template from classpath
     *
     * @param templatePath Path stored in DB (email-templates/welcome-email.html)
     * @return HTML content as String
     */
    public String loadTemplate(String templatePath) {

        try {
            ClassPathResource resource = new ClassPathResource(templatePath);

            try (InputStream inputStream = resource.getInputStream()) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load email template from path: " + templatePath,
                    e
            );
        }
    }
}
