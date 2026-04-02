package com.tutoring.notification;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.provider.EmailProvider;
import com.tutoring.notification.repository.NotificationRepository;
import com.tutoring.notification.service.EmailNotificationService;
import com.tutoring.notification.service.EmailTemplateLoader;
import com.tutoring.notification.service.TemplateRenderer;
import com.tutoring.notification.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailNotificationServiceTest {

    @Test
    void shouldSendEmailSuccessfully() {

        EmailProvider emailProvider = mock(EmailProvider.class);
        TemplateService templateService = mock(TemplateService.class);
        EmailTemplateLoader templateLoader = mock(EmailTemplateLoader.class);
        TemplateRenderer renderer = mock(TemplateRenderer.class);
        NotificationRepository repository = mock(NotificationRepository.class);

        EmailNotificationService service =
                new EmailNotificationService(
                        emailProvider,
                        templateService,
                        templateLoader,
                        renderer,
                        repository
                );

        Notification notification = Notification.builder()
                .id("123")
                .type(NotificationType.EMAIL)
                .recipient("test@example.com")
                .templateCode("welcome-email")
                .payload(Map.of("name", "Manoj"))
                .build();

        NotificationTemplate template = NotificationTemplate.builder()
                .code("welcome-email")
                .subject("Welcome!")
                .templatePath("welcome.html")
                .build();

        when(templateService.getActiveTemplate(any(), any()))
                .thenReturn(template);

        when(templateLoader.loadTemplate(any()))
                .thenReturn("<html>Hello {{name}}</html>");

        when(renderer.render(any(), any()))
                .thenReturn("<html>Hello Manoj</html>");

        service.send(notification);

        verify(emailProvider, times(1))
                .sendEmail(any(), any(), any());
    }
}