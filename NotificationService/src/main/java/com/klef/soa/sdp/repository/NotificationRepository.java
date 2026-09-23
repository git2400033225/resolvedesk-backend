package com.klef.soa.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.soa.sdp.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>
{
	
}