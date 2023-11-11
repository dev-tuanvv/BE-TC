package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Request;

import lombok.Data;

@Data
public class RequestResDto {
    private int id;
    private List<SubjectLevelResDto> subjects;
    private String gender;
    private String tutorLevel;
    private String districtName;
    private String provinceName;
    private float tuition; // fee
    private int slots;
    private int slotsLength;
    private int status;

    public void fromRequest(Request request) {
        this.id = request.getId();
        this.gender = request.getGender();
        this.tutorLevel = request.getTutorLevel();
        this.districtName = request.getDistrict().getName();
        this.provinceName = request.getDistrict().getProvince().getName();
        this.tuition = request.getTuition();
        this.slots = request.getSlots();
        this.slotsLength = request.getSlotsLength();
        this.status = request.getStatus();

    }
}
