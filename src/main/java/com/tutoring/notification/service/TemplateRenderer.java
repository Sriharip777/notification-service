package com.tutoring.notification.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TemplateRenderer {

    /**
     * Replaces {{key}} placeholders in HTML with values from payload
     *
     * @param templateContent Raw HTML template
     * @param payload Dynamic values (name, otp, date, etc.)
     * @return Rendered HTML
     */
    public String render(String templateContent, Map<String, Object> payload) {

        if (templateContent == null || payload == null || payload.isEmpty()) {
            return templateContent;
        }

        String renderedContent = templateContent;

        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            String value = String.valueOf(entry.getValue());

            renderedContent = renderedContent.replace(placeholder, value);
        }

        return renderedContent;
    }
}
