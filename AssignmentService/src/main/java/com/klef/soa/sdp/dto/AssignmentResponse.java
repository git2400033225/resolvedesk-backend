package com.klef.soa.sdp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignmentResponse
{
    private Long id;
    private Long complaintId;
    private Long assignedTo;
    private String department;
    private String status;
    private String remarks;
}