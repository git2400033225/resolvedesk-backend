package com.klef.soa.sdp.controller;

import java.util.List;
import com.klef.soa.sdp.dto.ComplaintResponse;

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
import org.springframework.security.access.prepost.PreAuthorize;

import com.klef.soa.sdp.dto.UserRequest;
import com.klef.soa.sdp.dto.UserResponse;
import com.klef.soa.sdp.service.UserService;
import com.klef.soa.sdp.dto.LoginRequest;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/user")
public class UserController 
{
    @Autowired
    private UserService service;


    @GetMapping("/")
    public String home()
    {
        return "User Service Project";
    }

    @GetMapping("/test")
    public String test()
    {
        return "Test API call";
    }

    @PostMapping("/add")
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserRequest request)
    {
        return new ResponseEntity<>(service.saveUser(request),HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/displayall")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return ResponseEntity.ok(service.getAllUsers());
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/display/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.getUserById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequest request)
    {
        return ResponseEntity.ok(service.updateUser(id, request));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'CUSTOMER')")
    @GetMapping("/{userId}/complaints")
    public ResponseEntity<List<ComplaintResponse>> getUserComplaints(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(
                service.getUserComplaints(userId));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id)
    {
        service.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully.");
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> userlogin(
            @Valid @RequestBody LoginRequest request)
    {
        UserResponse response =
                service.userLogin(request);

        return ResponseEntity.ok(response);
    }
}