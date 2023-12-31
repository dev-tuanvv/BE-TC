package com.tutorcenter.dto.clazz;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Clazz;

import lombok.Data;

@Data
public class ListClazzByTutorResDto {
    private int id;
    private int status;
    private String parentName;
    private String phone;
    private List<SubjectLevelResDto> subjects;
    private int slots;
    private int slotsLength;
    private float tuition;
    private String notes;
    private Date dateStart;
    private Date dateEnd;
    private String tutorLevel;
    private String address;
    private String districtName;
    private String provinceName;
    private String gender;
    private String tutorName;


    public void fromClazz(Clazz clazz) {
        this.id = clazz.getId();
        this.parentName = clazz.getRequest().getParent().getFullname();
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
        this.notes = clazz.getRequest().getNotes();
        this.phone = clazz.getRequest().getPhone();
        this.tutorName = clazz.getTutor().getFullname();
    }
}
