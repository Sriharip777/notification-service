package com.tutoring.notification;

import com.tutoring.notification.entity.Notification;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.provider.FirebasePushProvider;
import com.tutoring.notification.service.PushNotificationService;
import com.tutoring.notification.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class PushNotificationServiceTest {

    @Test
    void shouldSendPushNotification() {

        FirebasePushProvider pushProvider = Mockito.mock(FirebasePushProvider.class);
        TemplateService templateService = Mockito.mock(TemplateService.class);

        PushNotificationService service =
                new PushNotificationService(pushProvider, templateService);

        Notification notification = Notification.builder()
                .type(NotificationType.PUSH)
                .recipient("device-token-123")
                .templateCode("WELCOME_PUSH")
                .payload(Map.of("name", "Manoj"))
                .build();

        service.send(notification);

        Mockito.verify(pushProvider, Mockito.times(1))
                .sendPush(Mockito.any(), Mockito.any());
    }
}
