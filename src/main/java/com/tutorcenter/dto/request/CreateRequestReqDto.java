package com.tutorcenter.dto.request;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import com.tutorcenter.model.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRequestReqDto {

    @NotEmpty
    private String phone;
    @NotEmpty
    private String address;
    @NotEmpty
    private List<Integer> listSubjectId;
    private String gender;
    private String daysOfWeek;
    private String time;
    @NotNull
    @Min(0)
    private int slots;
    private int slotsLength;
    private float tuition;
    private String notes;
    private Date dateStart;
    private Date dateEnd;
    private int districtId;
    private String tutorLevel;

    public void toRequest(Request request) {
        request.setPhone(this.phone);
        request.setAddress(this.address);
        request.setDaysOfWeek(this.daysOfWeek);
        request.setTime(this.time);
        request.setSlots(this.slots);
        request.setSlotsLength(this.slotsLength);
        request.setTuition(this.tuition);
        request.setNotes(this.notes);
        request.setDateStart(this.dateStart);
        request.setDateEnd(this.dateEnd);
        request.setDateCreate(new Date(Calendar.getInstance().getTimeInMillis()));
        request.setDateModified(new Date(Calendar.getInstance().getTimeInMillis()));
        request.setStatus(0);
        request.setDeleted(false);
        request.setTutorLevel(this.tutorLevel);
        request.setGender(this.gender);
    }
}
