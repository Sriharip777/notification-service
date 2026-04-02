package com.tutoring.notification;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.provider.PushProvider;
import com.tutoring.notification.repository.NotificationRepository;
import com.tutoring.notification.service.PushNotificationService;
import com.tutoring.notification.service.TemplateService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PushNotificationServiceTest {

    @Test
    void shouldSendPushNotification() {

        PushProvider pushProvider = mock(PushProvider.class);
        TemplateService templateService = mock(TemplateService.class);
        NotificationRepository repository = mock(NotificationRepository.class);

        PushNotificationService service =
                new PushNotificationService(
                        pushProvider,
                        templateService,
                        repository
                );

        Notification notification = Notification.builder()
                .id("123")
                .type(NotificationType.PUSH)
                .recipient("device-token-123")
                .templateCode("WELCOME_PUSH")
                .payload(Map.of("name", "Manoj"))
                .build();

        NotificationTemplate template = NotificationTemplate.builder()
                .code("WELCOME_PUSH")
                .subject("Welcome!")
                .templatePath("push.html")
                .active(true)
                .build();

        when(templateService.getActiveTemplate(any(), any()))
                .thenReturn(template);

        service.send(notification);

        verify(pushProvider, times(1))
                .sendPush(any(), any());
    }
}