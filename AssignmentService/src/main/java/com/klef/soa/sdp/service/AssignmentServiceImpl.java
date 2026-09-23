package com.klef.soa.sdp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klef.soa.sdp.client.UserClient;
import com.klef.soa.sdp.dto.UserResponse;


import com.klef.soa.sdp.dto.AssignmentRequest;
import com.klef.soa.sdp.dto.AssignmentResponse;
import com.klef.soa.sdp.entity.Assignment;
import com.klef.soa.sdp.exception.ResourceNotFoundException;
import com.klef.soa.sdp.repository.AssignmentRepository;

@Service
public class AssignmentServiceImpl implements AssignmentService
{
	
	@Autowired
	private UserClient userClient;
	
    @Autowired
    private AssignmentRepository repository;

    @Override
    public AssignmentResponse saveAssignment(
            AssignmentRequest request)
    {
    	
    	  UserResponse user =
    	            userClient.getUserById(request.getAssignedTo());

    	    if (!user.getRole().equals("STAFF"))
    	    {
    	        throw new IllegalArgumentException(
    	                "User is not a STAFF");
    	    }
      
        Assignment assignment = Assignment.builder()
                .complaintId(request.getComplaintId())
                .assignedTo(request.getAssignedTo())
                .department(request.getDepartment())
                .status(request.getStatus())
                .remarks(request.getRemarks())
                .build();

        Assignment savedAssignment = repository.save(assignment);

        return mapToResponse(savedAssignment);
    }

    @Override
    public List<AssignmentResponse> getAllAssignments()
    {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentResponse getAssignmentById(Long id)
    {
        Assignment assignment = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment not found with id : " + id));

        return mapToResponse(assignment);
    }

    @Override
    public AssignmentResponse updateAssignment(
            Long id,
            AssignmentRequest request)
    {
        Assignment assignment = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment not found with id : " + id));

        assignment.setComplaintId(request.getComplaintId());
        assignment.setAssignedTo(request.getAssignedTo());
        assignment.setDepartment(request.getDepartment());
        assignment.setStatus(request.getStatus());
        assignment.setRemarks(request.getRemarks());

        Assignment updatedAssignment =
                repository.save(assignment);

        return mapToResponse(updatedAssignment);
    }

    @Override
    public void deleteAssignment(Long id)
    {
        Assignment assignment = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment not found with id : " + id));

        repository.delete(assignment);
    }

    private AssignmentResponse mapToResponse(
            Assignment assignment)
    {
        return AssignmentResponse.builder()
                .id(assignment.getId())
                .complaintId(assignment.getComplaintId())
                .assignedTo(assignment.getAssignedTo())
                .department(assignment.getDepartment())
                .status(assignment.getStatus())
                .remarks(assignment.getRemarks())
                .build();
    }
}