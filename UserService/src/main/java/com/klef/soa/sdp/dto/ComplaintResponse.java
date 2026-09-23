package com.klef.soa.sdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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