package com.tutoring.notification;

import com.tutoring.notification.entity.NotificationTemplate;
import com.tutoring.notification.entity.NotificationType;
import com.tutoring.notification.repository.NotificationTemplateRepository;
import com.tutoring.notification.service.TemplateService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TemplateServiceTest {

    @Test
    void shouldReturnActiveTemplate() {

        NotificationTemplateRepository repo =
                mock(NotificationTemplateRepository.class);

        NotificationTemplate template = NotificationTemplate.builder()
                .code("welcome-email")
                .type(NotificationType.EMAIL)
                .active(true)
                .build();

        when(
                repo.findByCodeAndTypeAndActiveTrue(
                        "welcome-email", NotificationType.EMAIL
                )
        ).thenReturn(Optional.of(template));

        TemplateService service = new TemplateService(repo);

        NotificationTemplate result =
                service.getActiveTemplate("welcome-email", NotificationType.EMAIL);

        assertNotNull(result);
        assertEquals("welcome-email", result.getCode());
    }
}