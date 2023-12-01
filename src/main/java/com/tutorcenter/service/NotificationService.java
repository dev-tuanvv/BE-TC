package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Notification;

@Service
public interface NotificationService {
    List<Notification> getNotificationsByUserId(int uId);

    Notification save(Notification notification);

    void read(int id);

    void unRead(int id);
}
