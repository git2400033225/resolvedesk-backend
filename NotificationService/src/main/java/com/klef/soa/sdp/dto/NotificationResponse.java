package com.klef.soa.sdp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse
{
    private Long id;
    private Long userId;
    private Long complaintId;
    private String message;
    private String status;
}