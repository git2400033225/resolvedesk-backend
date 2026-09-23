package com.klef.soa.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.soa.sdp.dto.ComplaintRequest;
import com.klef.soa.sdp.dto.ComplaintResponse;
import com.klef.soa.sdp.service.ComplaintService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/complaint")
public class ComplaintController
{
    @Autowired
    private ComplaintService service;
    @Value("${server.port}")
    private String port;

    @GetMapping("/")
    public String home()
    {
        return "Complaint Service Project";
    }

    @GetMapping("/test")
    public String test()
    {
        return "Test API call";
    }
    
    // Load Balancing - Instance Check
    @GetMapping("/instance")
    public String instance()
    {
        return "Complaint Service instance running on port: " + port;
    }

    @PostMapping("/add")
    public ResponseEntity<ComplaintResponse> saveComplaint(@Valid @RequestBody ComplaintRequest request)
    {
        return new ResponseEntity<>(
                service.saveComplaint(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/displayall")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints()
    {
    	
    	System.out.println(
    	        "Complaint Service instance running on port: " + port);
        return ResponseEntity.ok(
                service.getAllComplaints());
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<ComplaintResponse> getComplaintById(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(
                service.getComplaintById(id));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ComplaintResponse>> getComplaintsByUserId(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(
                service.getComplaintsByUserId(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ComplaintResponse> updateComplaint(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintRequest request)
    {
        return ResponseEntity.ok(
                service.updateComplaint(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComplaint(
            @PathVariable Long id)
    {
        service.deleteComplaint(id);

        return ResponseEntity.ok(
                "Complaint deleted successfully.");
    }
}