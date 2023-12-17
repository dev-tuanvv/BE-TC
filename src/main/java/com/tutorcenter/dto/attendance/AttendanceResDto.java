package com.tutorcenter.dto.attendance;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorcenter.model.Attendance;

import lombok.Data;

@Data
public class AttendanceResDto {
    private int id;
    private int clazzId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dateCreate;
    private int status;

    public void fromAttendance(Attendance attendance) {
        this.id = attendance.getId();
        this.clazzId = attendance.getClazz().getId();
        this.dateCreate = new Date(System.currentTimeMillis());
        this.status = attendance.getStatus();
    }

}
