package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Request;

import lombok.Data;

@Data
public class RequestResDto {
    private int id;
    private String parentFulName;
    private List<SubjectLevelResDto> subjects;
    private String gender;
    private String tutorLevel;
    private String districtName;
    private String provinceName;
    private float tuition; // fee
    private String daysOfWeek;
    private int slots;
    private int slotsLength;
    private int status;
    private String address;
    private Date dateCreate;

    public void fromRequest(Request request) {
        this.parentFulName = request.getParent().getFullname();
        this.id = request.getId();
        this.gender = request.getGender();
        this.tutorLevel = request.getTutorLevel();
        this.districtName = request.getDistrict().getName();
        this.provinceName = request.getDistrict().getProvince().getName();
        this.tuition = request.getTuition();
        this.daysOfWeek = request.getDaysOfWeek();
        this.slots = request.getSlots();
        this.slotsLength = request.getSlotsLength();
        this.status = request.getStatus();
        this.address = request.getAddress();
        this.dateCreate = request.getDateCreate();
    }
}
