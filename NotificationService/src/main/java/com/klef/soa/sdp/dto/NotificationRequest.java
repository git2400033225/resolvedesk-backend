package com.klef.soa.sdp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest
{
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Complaint ID is required")
    private Long complaintId;

    @NotBlank(message = "Message is required")
    private String message;

    @NotBlank(message = "Status is required")
    private String status;
}