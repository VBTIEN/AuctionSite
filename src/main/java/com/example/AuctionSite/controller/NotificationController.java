package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.NotificationRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.NotificationResponse;
import com.example.AuctionSite.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {
    NotificationService notificationService;
    
    @PostMapping("/create_notification")
    ApiResponse<NotificationResponse> createNotification(@RequestBody NotificationRequest notificationRequest) {
        return ApiResponse.<NotificationResponse>builder()
            .result(notificationService.createNotification(notificationRequest))
            .build();
    }
    
    @GetMapping("/get_all_notifications")
    ApiResponse<List<NotificationResponse>> getAllNotifications() {
        return ApiResponse.<List<NotificationResponse>>builder()
            .result(notificationService.getAllNotifications())
            .build();
    }
    
    @GetMapping("/get_notification_by_name/{name}")
    ApiResponse<NotificationResponse> getNotificationByName(@PathVariable("name") String name) {
        return ApiResponse.<NotificationResponse>builder()
            .result(notificationService.getNotificationByName(name))
            .build();
    }
    
    @PutMapping("/update_notification/{name}")
    ApiResponse<NotificationResponse> updateNotification(@PathVariable("name") String name, @RequestBody NotificationRequest notificationRequest) {
        return ApiResponse.<NotificationResponse>builder()
            .result(notificationService.updateNotification(name, notificationRequest))
            .build();
    }
    
    @DeleteMapping("/delete_notification/{name}")
    ApiResponse<String> deleteNotification(@PathVariable("name") String name) {
        notificationService.deleteNotification(name);
        return ApiResponse.<String>builder()
            .result("Notification deleted")
            .build();
    }
}
