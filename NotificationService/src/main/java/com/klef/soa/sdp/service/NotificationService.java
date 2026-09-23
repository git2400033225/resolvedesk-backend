package com.klef.soa.sdp.service;

import java.util.List;

import com.klef.soa.sdp.dto.NotificationRequest;
import com.klef.soa.sdp.dto.NotificationResponse;

public interface NotificationService
{
    NotificationResponse saveNotification(NotificationRequest request);

    List<NotificationResponse> getAllNotifications();

    NotificationResponse getNotificationById(Long id);

    NotificationResponse updateNotification(
            Long id,
            NotificationRequest request);

   
}