package com.tutoring.notification.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * KafkaConfig
 *
 * This configuration is intentionally minimal.
 * It exists to match the module architecture
 * and to support future event-based notifications.
 *
 * No consumers or listeners are enabled right now.
 */
@Configuration
@EnableKafka
public class KafkaConfig {
    // 🔕 Intentionally empty for now
}
