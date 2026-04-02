CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               user_id VARCHAR(100),
                               channel VARCHAR(20),
                               recipient VARCHAR(255),
                               template_code VARCHAR(100),
                               payload JSONB,
                               status VARCHAR(20),
                               retry_count INT DEFAULT 0,
                               failure_reason TEXT,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               sent_at TIMESTAMP
);
V2__create_notification_templates_table.sql
