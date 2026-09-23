package com.klef.soa.sdp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintResponse
{
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String category;
    private String priority;
    private String status;
}