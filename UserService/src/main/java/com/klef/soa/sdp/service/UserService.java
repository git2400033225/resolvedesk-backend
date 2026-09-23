package com.klef.soa.sdp.service;

import java.util.List;

import com.klef.soa.sdp.dto.LoginRequest;
import com.klef.soa.sdp.dto.ComplaintResponse;
import com.klef.soa.sdp.dto.UserRequest;
import com.klef.soa.sdp.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
public interface UserService 
{
    UserResponse saveUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
    
    UserResponse userLogin(LoginRequest request);

    UserDetails loadUserByUsername(String username);
    List<ComplaintResponse> getUserComplaints(Long userId);
}
