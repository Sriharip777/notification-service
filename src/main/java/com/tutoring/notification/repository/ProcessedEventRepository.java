package com.tutoring.notification.repository;

import com.tutoring.notification.entity.ProcessedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProcessedEventRepository
        extends MongoRepository<ProcessedEvent, String> {

    Optional<ProcessedEvent> findByEventId(String eventId);
}
