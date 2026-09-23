package com.klef.soa.sdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class ComplaintServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(
                ComplaintServiceApplication.class, args);

        System.out.println(
                "Complaint Service Project is running ...........!!");
    }
}