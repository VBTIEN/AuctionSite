package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.NotificationRequest;
import com.example.AuctionSite.dto.response.NotificationResponse;
import com.example.AuctionSite.entity.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(NotificationRequest notificationRequest);

    NotificationResponse toNotificationResponse(Notification notification);

    void toUpdateNotification(@MappingTarget Notification notification, NotificationRequest notificationRequest);
}
