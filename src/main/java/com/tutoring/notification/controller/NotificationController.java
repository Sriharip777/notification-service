package com.tutoring.notification.controller;

import com.tutoring.notification.dto.EmailNotificationRequest;
import com.tutoring.notification.dto.PushNotificationRequest;
import com.tutoring.notification.entity.NotificationStatus;
import com.tutoring.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // ================= EMAIL =================
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(
            @RequestBody EmailNotificationRequest request) {

        notificationService.sendEmail(request);
        return ResponseEntity.ok("Email notification request received");
    }

    // ================= PUSH =================
    @PostMapping("/push")
    public ResponseEntity<String> sendPush(
            @RequestBody PushNotificationRequest request) {

        notificationService.sendPush(request);
        return ResponseEntity.ok("Push notification request received");
    }

    // ================= HISTORY =================

    @GetMapping
    public ResponseEntity<?> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {

        NotificationStatus enumStatus;
        try {
            enumStatus = NotificationStatus.valueOf(status.toUpperCase());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body("Invalid status: " + status);
        }

        return ResponseEntity.ok(notificationService.getByStatus(enumStatus));
    }
}
