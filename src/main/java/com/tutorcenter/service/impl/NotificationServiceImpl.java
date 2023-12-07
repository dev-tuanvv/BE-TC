package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Notification;
import com.tutorcenter.model.User;
import com.tutorcenter.repository.NotificationRepository;
import com.tutorcenter.service.NotificationService;

@Component
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationsByUserId(int uId) {
        List<Notification> response = new ArrayList<>();
        for (Notification n : notificationRepository.findAll()) {
            if (n.getUser().getId() == uId)
                response.add(n);
        }
        return response;
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void read(int id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void unRead(int id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    @Override
    public Notification add(User user, String content) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setRead(false);
        notification.setTimeCreate(new Date(System.currentTimeMillis()));
        notification.setUser(user);
        return notificationRepository.save(notification);
    }
}
