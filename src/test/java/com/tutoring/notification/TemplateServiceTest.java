package com.tutoring.notification;

import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.repository.NotificationTemplateRepository;
import com.tutoring.notification.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TemplateServiceTest {

    @Test
    void shouldReturnActiveTemplate() {

        NotificationTemplateRepository repo =
                Mockito.mock(NotificationTemplateRepository.class);

        NotificationTemplate template = NotificationTemplate.builder()
                .templateCode("welcome-email")
                .type(NotificationType.EMAIL)
                .active(true)
                .build();

        Mockito.when(
                repo.findByTemplateCodeAndTypeAndActiveTrue(
                        "welcome-email", NotificationType.EMAIL
                )
        ).thenReturn(Optional.of(template));

        TemplateService service = new TemplateService(repo);

        NotificationTemplate result =
                service.getActiveTemplate("welcome-email", NotificationType.EMAIL);

        assertNotNull(result);
        assertEquals("welcome-email", result.getTemplateCode());
    }
}
