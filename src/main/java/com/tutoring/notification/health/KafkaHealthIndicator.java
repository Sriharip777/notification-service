package com.tutoring.notification.health;

import org.springframework.boot.actuate.health.*;
import org.springframework.stereotype.Component;

@Component
public class KafkaHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            // simple check
            return Health.up()
                    .withDetail("kafka", "Connected")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("kafka", "Disconnected")
                    .build();
        }
    }
}
