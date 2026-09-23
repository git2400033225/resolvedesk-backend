package com.klef.soa.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.soa.sdp.dto.NotificationRequest;
import com.klef.soa.sdp.dto.NotificationResponse;
import com.klef.soa.sdp.service.NotificationService;

import jakarta.validation.Valid;


@RestController
@Validated
@RequestMapping("/notification")
public class NotificationController
{
    @Autowired
    private NotificationService service;

    @GetMapping("/")
    public String home()
    {
        return "Notification Service Project";
    }

    @GetMapping("/test")
    public String test()
    {
        return "Test API call";
    }

    @PostMapping("/add")
    public ResponseEntity<NotificationResponse> saveNotification(
            @Valid @RequestBody NotificationRequest request)
    {
        return new ResponseEntity<>(
                service.saveNotification(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/displayall")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications()
    {
        return ResponseEntity.ok(
                service.getAllNotifications());
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(
                service.getNotificationById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody NotificationRequest request)
    {
        return ResponseEntity.ok(
                service.updateNotification(id, request));
    }

   
}