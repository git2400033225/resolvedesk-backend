package com.klef.soa.sdp.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.soa.sdp.dto.LoginRequest;
import com.klef.soa.sdp.dto.UserRequest;
import com.klef.soa.sdp.dto.UserResponse;
import com.klef.soa.sdp.entity.Role;
import com.klef.soa.sdp.entity.User;
import com.klef.soa.sdp.exception.ResourceNotFoundException;
import com.klef.soa.sdp.exception.UnauthorizedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.klef.soa.sdp.repository.UserRepository;
import com.klef.soa.sdp.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import com.klef.soa.sdp.client.ComplaintClient;
import com.klef.soa.sdp.dto.ComplaintResponse;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService 
{
    @Autowired
    private UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    private ComplaintClient complaintClient;

    private final JwtUtil jwtUtil;

    @Override
    public UserResponse saveUser(UserRequest request) 
    {
        Role userRole;

        try 
        {
            userRole = Role.valueOf(request.getRole().trim().toUpperCase());
        } 
        catch (Exception e) 
        {
            throw new IllegalArgumentException(
                    "Role must be one of CUSTOMER, STAFF, ADMIN");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .contact(request.getContact())
                .role(userRole)
                .build();

        User savedUser = repository.save(user);

        return mapToResponse(savedUser);
    }
    
    

    @Override
    public List<UserResponse> getAllUsers() 
    {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    
    

    @Override
    public UserResponse getUserById(Long id) 
    {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id));

        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) 
    {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id));

        Role userRole;

        try 
        {
            userRole = Role.valueOf(request.getRole().trim().toUpperCase());
        } 
        catch (Exception e) 
        {
            throw new IllegalArgumentException(
                    "Role must be one of CUSTOMER, STAFF, ADMIN");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setContact(request.getContact());
        user.setRole(userRole);

        User updatedUser = repository.save(user);

        return mapToResponse(updatedUser);
    }

    
    
    
    @Override
    public void deleteUser(Long id) 
    {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id));

        repository.delete(user);
    }

    private UserResponse mapToResponse(User user) 
    {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .contact(user.getContact())
                .role(user.getRole().name())
                .build();
    }
    private UserResponse mapToResponseWithToken(
            User user,
            String token)
    {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .contact(user.getContact())
                .role(user.getRole().name())
                .token(token)
                .build();
    }
    
    @Override
    public UserResponse userLogin(LoginRequest request)
    {
        User user = repository.findByEmail(request.getEmail())
                .filter(foundUser -> passwordEncoder.matches(
                        request.getPassword(),
                        foundUser.getPassword()))
                .orElseThrow(() ->
                        new UnauthorizedException(
                                "Invalid Email or Password"));

        UserDetails userDetails =
                loadUserByUsername(user.getEmail());

        String token =
                jwtUtil.generateToken(userDetails);

        return mapToResponseWithToken(user, token);
    }
    
    
    
    @Override
    public UserDetails loadUserByUsername(String username)
    {
        User user = repository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole().name()))
         
                .build();
    }
    
    
    
    @Override
    @CircuitBreaker(
            name = "ComplaintService",
            fallbackMethod = "ComplaintServiceFallback"
    )
    public List<ComplaintResponse> getUserComplaints(Long userId)
    {
        List<ComplaintResponse> complaints =
                complaintClient.getComplaintsByUserId(userId);

        System.out.println("Complaint data received successfully");
        System.out.println(complaints);

        return complaints;
    }

    public List<ComplaintResponse> ComplaintServiceFallback(Throwable throwable)
    {
        System.out.println("==========================");
        System.out.println("Complaint Service is unavailable");
        System.out.println("Circuit Breaker fallback executed");
        System.out.println("==========================");

        return Collections.emptyList();
    }
}