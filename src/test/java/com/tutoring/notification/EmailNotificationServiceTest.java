package com.tutoring.notification;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.provider.EmailProvider;
import com.tutoring.notification.service.EmailNotificationService;
import com.tutoring.notification.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class EmailNotificationServiceTest {

    @Test
    void shouldSendEmailSuccessfully() {

        EmailProvider emailProvider = Mockito.mock(EmailProvider.class);
        TemplateService templateService = Mockito.mock(TemplateService.class);

        EmailNotificationService service =
                new EmailNotificationService(emailProvider, templateService);

        Notification notification = Notification.builder()
                .type(NotificationType.EMAIL)
                .recipient("test@example.com")
                .templateCode("welcome-email")
                .payload(Map.of("name", "Manoj"))
                .build();

        service.send(notification);

        Mockito.verify(emailProvider, Mockito.times(1))
                .sendEmail(Mockito.any(), Mockito.any(), Mockito.any());
    }
}
