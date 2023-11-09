package com.tutorcenter.dto.clazz;

import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Clazz;

import lombok.Data;

@Data
public class ListClazzResDto {
    private int id;
    private int requestId;
    private List<SubjectLevelResDto> subjects;
    private int status;
    private String address;
    private String gender;
    private float tuition;

    public void fromClazz(Clazz clazz) {
        this.id = clazz.getId();
        this.requestId = clazz.getRequest().getId();
        this.status = clazz.getStatus();
        this.address = clazz.getRequest().getAddress();
        this.gender = clazz.getRequest().getGender();
        this.tuition = clazz.getRequest().getTuition();
    }
}
