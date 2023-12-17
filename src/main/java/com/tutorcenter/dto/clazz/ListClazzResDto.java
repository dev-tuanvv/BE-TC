package com.tutorcenter.dto.clazz;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Tutor;

import lombok.Data;

@Data
public class ListClazzResDto {
    private int id;
    private int requestId;
    private String parentName;
    private List<SubjectLevelResDto> subjects;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String daysOfWeek;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String time;
    private int slots;
    private int slotsLength;
    private String tutorLevel;
    private String address;
    private String districtName;
    private String provinceName;
    private String gender;
    private float tuition;
    private int status;
    private Date dateStart;
    private Date dateEnd;
    private TutorDto tutor = new TutorDto();

    public void fromClazz(Clazz clazz) {
        this.id = clazz.getId();
        this.requestId = clazz.getRequest().getId();
        this.parentName = clazz.getRequest().getParent().getFullname();
        this.daysOfWeek = clazz.getRequest().getDaysOfWeek();
        this.time = clazz.getRequest().getTimeTutoring();
        this.slots = clazz.getRequest().getSlots();
        this.slotsLength = clazz.getRequest().getSlotsLength();
        this.tutorLevel = clazz.getRequest().getTutorLevel();
        this.address = clazz.getRequest().getAddress();
        this.districtName = clazz.getRequest().getDistrict().getName();
        this.provinceName = clazz.getRequest().getDistrict().getProvince().getName();
        this.gender = clazz.getRequest().getGender();
        this.tuition = clazz.getRequest().getTuition();
        this.status = clazz.getStatus();
        this.dateStart = clazz.getRequest().getDateStart();
        this.dateEnd = clazz.getRequest().getDateEnd();
    }

    public void fromTutor(Tutor tutor) {
        this.tutor.setId(tutor.getId());
        this.tutor.setName(tutor.getFullname());
        this.tutor.setUniversity(tutor.getUniversity());
        this.tutor.setAddress(tutor.getAddress());
        this.tutor.setDistrictName(tutor.getDistrict().getName());
        this.tutor.setProvinceName(tutor.getDistrict().getProvince().getName());
    }
}

@Data
class TutorDto {
    private int id;
    private String name;
    private String university;
    private String address;
    private String districtName;
    private String provinceName;
}
