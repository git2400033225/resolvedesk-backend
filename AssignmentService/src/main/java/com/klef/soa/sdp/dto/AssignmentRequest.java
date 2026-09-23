package com.klef.soa.sdp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignmentRequest
{
    @NotNull(message = "Complaint ID is required")
    private Long complaintId;

    @NotNull(message = "Assigned To is required")
    private Long assignedTo;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Status is required")
    private String status;
    
    
  String remarks;
}