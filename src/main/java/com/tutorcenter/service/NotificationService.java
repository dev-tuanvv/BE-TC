package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Notification;
import com.tutorcenter.model.User;

@Service
public interface NotificationService {
    List<Notification> getNotificationsByUserId(int uId);

    Notification save(Notification notification);

    Notification add(User user, String content);

    void read(int id);

    void unRead(int id);
}
