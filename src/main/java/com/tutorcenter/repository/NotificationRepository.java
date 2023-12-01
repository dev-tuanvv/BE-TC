package com.tutorcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT t FROM tbl_notification n WHERE n.user_id = ?")
    List<Notification> getNotificationsByUserId(int uId);
}
