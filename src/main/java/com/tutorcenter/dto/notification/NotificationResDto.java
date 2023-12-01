package com.tutorcenter.dto.notification;

import java.util.Date;

import com.tutorcenter.model.Notification;

import lombok.Data;

@Data
public class NotificationResDto {
    private int id;
    private String content;
    private Date time_create;
    private boolean read;

    public void fromNotification(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.time_create = notification.getTimeCreate();
        this.read = notification.isRead();
    }
}
