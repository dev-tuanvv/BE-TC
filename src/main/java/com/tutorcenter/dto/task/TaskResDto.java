package com.tutorcenter.dto.task;

import com.tutorcenter.model.Task;

import lombok.Data;

@Data
public class TaskResDto {
    private int id;
    private String name;
    private int requestId;
    private int type;
    private int managerId;
    private int status;
    private String link;

    public void fromTask(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.requestId = task.getRequestId();
        this.type = task.getType();
        this.managerId = task.getManager().getId();
        this.status = task.getStatus();
        if (this.type == 1) {
            this.link = "/api/request/" + this.requestId;
        } else if (this.type == 2) {
            this.link = "/api/requestVerification/" + this.requestId;
        }
    }
}
