package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Notification;
import com.tutorcenter.repository.NotificationRepository;
import com.tutorcenter.service.NotificationService;

@Component
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationsByUserId(int uId) {
        return notificationRepository.getNotificationsByUserId(uId);
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
}
