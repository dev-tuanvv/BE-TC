package com.tutorcenter.dto.clazz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.service.FeedbackService;

import lombok.Data;

@Data
public class ClazzDetailResDto {
    @Autowired
    private FeedbackService feedbackService;

    private int id;

    private String parentName;

    private float tuition;

    private String phone;

    private String address;

    private String districtName;

    private String provinceName;

    private List<SubjectLevelResDto> subjects;

    private String notes;

    private java.sql.Date dateStart;

    private java.sql.Date dateEnd;

    private int status;

    private String daysOfWeek;
    private String time;

    private int slots;

    private int attendances;

    private String feedback;

    private int rating;

    public void fromClazz(Clazz clazz) {
        this.id = clazz.getId();
        this.parentName = clazz.getRequest().getParent().getFullname();
        this.tuition = clazz.getRequest().getTuition();
        this.phone = clazz.getRequest().getPhone();
        this.address = clazz.getRequest().getAddress();
        this.districtName = clazz.getRequest().getDistrict().getName();
        this.provinceName = clazz.getRequest().getDistrict().getProvince().getName();
        this.notes = clazz.getRequest().getNotes();
        this.dateStart = clazz.getRequest().getDateStart();
        this.dateEnd = clazz.getRequest().getDateEnd();
        this.status = clazz.getStatus();
        this.daysOfWeek = clazz.getRequest().getDaysOfWeek();
        this.time = clazz.getRequest().getTimeTutoring();
        this.slots = clazz.getRequest().getSlots();

        if (clazz.getFeedback() != null) {
            this.feedback = clazz.getFeedback().getContent();
            this.rating = clazz.getFeedback().getRating();
        }

    }
}
