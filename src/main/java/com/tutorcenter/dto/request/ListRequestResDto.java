package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.Subject;

import lombok.Data;

@Data
public class ListRequestResDto {
    private int id;

    private String parentName;

    private Date dateCreate;

    private int status;

    private List<SubjectLevelResDto> subjects;

    public void fromRequest(Request request) {
        this.id = request.getId();
        this.parentName = request.getParent().getFullname();
        this.dateCreate = request.getDateCreate();
        this.status = request.getStatus();

    }
}
