package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.model.Request;

import lombok.Data;

@Data
public class RequestResDto {
    private int requestId;
    private String parentName;
    private List<String> subjects;
    private String levels;
    private int status;
    private Date dateCreate;

    public void fromRequest(Request request) {
        requestId = request.getId();
        parentName = request.getParent().getFullname();
        status = request.getStatus();
        dateCreate = request.getDateCreate();
    }
}
