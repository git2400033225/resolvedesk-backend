package com.klef.soa.sdp.dto;

import lombok.*;

@Data
public class UserResponse 
{
    private Long id;
    private String name;
    private String email;
    private String contact;
    private String role;
}