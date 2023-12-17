package com.tutorcenter.dto.task;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dateCreate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dateFinished;

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
        this.dateCreate = task.getDateCreate();
        this.dateFinished = task.getDateFinished();
    }
}
