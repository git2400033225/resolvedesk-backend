package com.klef.soa.sdp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.soa.sdp.dto.NotificationRequest;
import com.klef.soa.sdp.dto.NotificationResponse;
import com.klef.soa.sdp.entity.Notification;
import com.klef.soa.sdp.exception.ResourceNotFoundException;
import com.klef.soa.sdp.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService
{
    @Autowired
    private NotificationRepository repository;

    @Override
    public NotificationResponse saveNotification(
            NotificationRequest request)
    {
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .complaintId(request.getComplaintId())
                .message(request.getMessage())
                .status(request.getStatus())
                .build();

        Notification savedNotification =
                repository.save(notification);

        return mapToResponse(savedNotification);
    }

    @Override
    public List<NotificationResponse> getAllNotifications()
    {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponse getNotificationById(Long id)
    {
        Notification notification = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification not found with id : " + id));

        return mapToResponse(notification);
    }

    @Override
    public NotificationResponse updateNotification(
            Long id,
            NotificationRequest request)
    {
        Notification notification = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification not found with id : " + id));

        notification.setUserId(request.getUserId());
        notification.setComplaintId(request.getComplaintId());
        notification.setMessage(request.getMessage());
        notification.setStatus(request.getStatus());

        Notification updatedNotification =
                repository.save(notification);

        return mapToResponse(updatedNotification);
    }

    

    private NotificationResponse mapToResponse(
            Notification notification)
    {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .complaintId(notification.getComplaintId())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .build();
    }

	
		
		
		
	}
