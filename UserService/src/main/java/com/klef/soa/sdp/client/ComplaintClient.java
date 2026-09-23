package com.klef.soa.sdp.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.klef.soa.sdp.dto.ComplaintResponse;

@FeignClient(name = "ComplaintService")
public interface ComplaintClient
{
    @GetMapping("/complaint/user/{userId}")
    List<ComplaintResponse> getComplaintsByUserId(
            @PathVariable Long userId);
}