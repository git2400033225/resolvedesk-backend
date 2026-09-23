package com.klef.soa.sdp.service;

import java.util.List;

import com.klef.soa.sdp.dto.ComplaintRequest;
import com.klef.soa.sdp.dto.ComplaintResponse;

public interface ComplaintService
{
    ComplaintResponse saveComplaint(ComplaintRequest request);

    List<ComplaintResponse> getAllComplaints();

    ComplaintResponse getComplaintById(Long id);

    ComplaintResponse updateComplaint(Long id, ComplaintRequest request);

    void deleteComplaint(Long id);
    
    List<ComplaintResponse> getComplaintsByUserId(Long userId);
}