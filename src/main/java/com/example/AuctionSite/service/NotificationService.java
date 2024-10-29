package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.NotificationRequest;
import com.example.AuctionSite.dto.response.NotificationResponse;
import com.example.AuctionSite.entity.Notification;
import com.example.AuctionSite.mapper.NotificationMapper;
import com.example.AuctionSite.repository.NotificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class NotificationService {
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;
    
    @PreAuthorize("hasAuthority('CREATE_NOTIFICATION')")
    public NotificationResponse createNotification(NotificationRequest notificationRequest) {
        Notification notification = notificationMapper.toNotification(notificationRequest);
        return notificationMapper.toNotificationResponse(notificationRepository.save(notification));
    }
    
    @PreAuthorize("hasAuthority('GET_ALL_NOTIFICATIONS')")
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream().map(notificationMapper::toNotificationResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('GET_NOTIFICATION_BY_NAME')")
    public NotificationResponse getNotificationByName(String name) {
        Notification notification = notificationRepository.findById(name).orElseThrow(() -> new RuntimeException("Notification not found"));
        return notificationMapper.toNotificationResponse(notification);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_NOTIFICATION')")
    public NotificationResponse updateNotification(String name, NotificationRequest notificationRequest) {
        Notification notification = notificationRepository.findById(name).orElseThrow(() -> new RuntimeException("Notification not found"));
        notificationMapper.toUpdateNotification(notification, notificationRequest);
        return notificationMapper.toNotificationResponse(notificationRepository.save(notification));
    }
    
    @PreAuthorize("hasAuthority('DELETE_NOTIFICATION')")
    public void deleteNotification(String name) {
        notificationRepository.deleteById(name);
    }
}
