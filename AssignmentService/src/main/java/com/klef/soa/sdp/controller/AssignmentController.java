package com.klef.soa.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


import com.klef.soa.sdp.dto.AssignmentRequest;
import com.klef.soa.sdp.dto.AssignmentResponse;
import com.klef.soa.sdp.service.AssignmentService;

import jakarta.validation.Valid;


@RestController
@Validated
@RequestMapping("/assignment")
public class AssignmentController
{
    @Autowired
    private AssignmentService service;
   

    @GetMapping("/")
    public String home()
    {
        return "Assignment Service Project";
    }

    @GetMapping("/test")
    public String test()
    {
        return "Test API call";
    }

    @PostMapping("/add")
    public ResponseEntity<AssignmentResponse> saveAssignment(
            @Valid @RequestBody AssignmentRequest request)
    {
        return new ResponseEntity<>(
                service.saveAssignment(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/displayall")
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments()
    {
        return ResponseEntity.ok(
                service.getAllAssignments());
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<AssignmentResponse> getAssignmentById(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(
                service.getAssignmentById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AssignmentResponse> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentRequest request)
    {
        return ResponseEntity.ok(
                service.updateAssignment(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAssignment(
            @PathVariable Long id)
    {
        service.deleteAssignment(id);

        return ResponseEntity.ok(
                "Assignment deleted successfully.");
    }
   
}