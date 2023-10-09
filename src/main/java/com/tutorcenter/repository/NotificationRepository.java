package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
