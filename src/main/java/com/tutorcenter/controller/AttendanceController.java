package com.tutorcenter.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.attendance.AttendanceResDto;
import com.tutorcenter.model.Attendance;
import com.tutorcenter.service.AttendanceService;
import com.tutorcenter.service.ClazzService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private ClazzService clazzService;

    @GetMapping("")
    public ApiResponseDto<List<AttendanceResDto>> getListAttendance() {
        List<Attendance> attendances = attendanceService.findAll();
        List<AttendanceResDto> response = new ArrayList<>();
        for (Attendance attendance : attendances) {
            AttendanceResDto dto = new AttendanceResDto();
            dto.fromAttendance(attendance);
            response.add(dto);
        }

        return ApiResponseDto.<List<AttendanceResDto>>builder().data(response).build();
    }

    @GetMapping("/clazz/{cId}")
    public ApiResponseDto<List<AttendanceResDto>> getListAttendanceByClazzId(@PathVariable int cId) {
        List<Attendance> attendances = attendanceService.getAttendancesByClazzId(cId);
        List<AttendanceResDto> response = new ArrayList<>();
        for (Attendance attendance : attendances) {
            AttendanceResDto dto = new AttendanceResDto();
            dto.fromAttendance(attendance);
            response.add(dto);
        }

        return ApiResponseDto.<List<AttendanceResDto>>builder().data(response).build();
    }

    @PostMapping("/create")
    public ApiResponseDto<AttendanceResDto> create(@RequestParam(name = "clazzId") int clazzId,
            @RequestParam(name = "status") int status) {
        Attendance attendance = new Attendance();
        AttendanceResDto dto = new AttendanceResDto();
        attendance.setClazz(clazzService.getClazzById(clazzId).orElse(null));
        attendance.setStatus(status);
        attendance.setDateCreate(new Date(System.currentTimeMillis()));

        dto.fromAttendance(attendanceService.save(attendance));

        return ApiResponseDto.<AttendanceResDto>builder().data(dto).build();
    }
}
