package com.klef.soa.sdp.service;

import java.util.List;

import com.klef.soa.sdp.dto.AssignmentRequest;
import com.klef.soa.sdp.dto.AssignmentResponse;

public interface AssignmentService
{
    AssignmentResponse saveAssignment(AssignmentRequest request);

    List<AssignmentResponse> getAllAssignments();

    AssignmentResponse getAssignmentById(Long id);

    AssignmentResponse updateAssignment(Long id,AssignmentRequest request);

    void deleteAssignment(Long id);
}