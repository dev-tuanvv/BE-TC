package com.tutorcenter.dto.attendance;

import java.sql.Date;

import com.tutorcenter.model.Attendance;

import lombok.Data;

@Data
public class AttendanceResDto {
    private int id;
    private int clazzId;
    private Date dateCreate;
    private int status;

    public void fromAttendance(Attendance attendance) {
        this.id = attendance.getId();
        this.clazzId = attendance.getClazz().getId();
        this.dateCreate = new Date(System.currentTimeMillis());
        this.status = attendance.getStatus();
    }
}
