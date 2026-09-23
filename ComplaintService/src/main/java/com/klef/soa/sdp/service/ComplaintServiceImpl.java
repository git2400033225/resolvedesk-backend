package com.klef.soa.sdp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.soa.sdp.dto.ComplaintRequest;
import com.klef.soa.sdp.dto.ComplaintResponse;
import com.klef.soa.sdp.entity.Complaint;
import com.klef.soa.sdp.exception.ResourceNotFoundException;
import com.klef.soa.sdp.repository.ComplaintRepository;

@Service
public class ComplaintServiceImpl implements ComplaintService
{
    @Autowired
    private ComplaintRepository repository;

    @Override
    public ComplaintResponse saveComplaint(ComplaintRequest request)
    {
        Complaint complaint = Complaint.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .priority(request.getPriority())
                .status(request.getStatus())
                .build();

        Complaint savedComplaint = repository.save(complaint);

        return mapToResponse(savedComplaint);
    }

    @Override
    public List<ComplaintResponse> getAllComplaints()
    {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ComplaintResponse getComplaintById(Long id)
    {
        Complaint complaint = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Complaint not found with id : " + id));

        return mapToResponse(complaint);
    }

    @Override
    public ComplaintResponse updateComplaint(
            Long id,
            ComplaintRequest request)
    {
        Complaint complaint = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Complaint not found with id : " + id));

        complaint.setUserId(request.getUserId());
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setCategory(request.getCategory());
        complaint.setPriority(request.getPriority());
        complaint.setStatus(request.getStatus());

        Complaint updatedComplaint = repository.save(complaint);

        return mapToResponse(updatedComplaint);
    }

    @Override
    public void deleteComplaint(Long id)
    {
        Complaint complaint = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Complaint not found with id : " + id));

        repository.delete(complaint);
    }

    private ComplaintResponse mapToResponse(Complaint complaint)
    {
        return ComplaintResponse.builder()
                .id(complaint.getId())
                .userId(complaint.getUserId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory())
                .priority(complaint.getPriority())
                .status(complaint.getStatus())
                .build();
    }
    
    
    @Override
    public List<ComplaintResponse> getComplaintsByUserId(Long userId)
    {
        return repository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
