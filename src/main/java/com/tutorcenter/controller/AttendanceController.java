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
import com.tutorcenter.model.Clazz;
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

        List<AttendanceResDto> response = new ArrayList<>();
        try {
            List<Attendance> attendances = attendanceService.findAll();

            for (Attendance attendance : attendances) {
                AttendanceResDto dto = new AttendanceResDto();
                dto.fromAttendance(attendance);
                response.add(dto);
            }
        } catch (Exception e) {
            return ApiResponseDto.<List<AttendanceResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<AttendanceResDto>>builder().data(response).build();

    }

    @GetMapping("/clazz/{cId}")
    public ApiResponseDto<List<AttendanceResDto>> getListAttendanceByClazzId(@PathVariable int cId) {
        List<AttendanceResDto> response = new ArrayList<>();
        try {
            List<Attendance> attendances = attendanceService.getAttendancesByClazzId(cId);

            for (Attendance attendance : attendances) {
                AttendanceResDto dto = new AttendanceResDto();
                dto.fromAttendance(attendance);
                response.add(dto);
            }
        } catch (Exception e) {
            return ApiResponseDto.<List<AttendanceResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<AttendanceResDto>>builder().data(response).build();
    }

    @PostMapping("/create")
    public ApiResponseDto<AttendanceResDto> create(@RequestParam(name = "clazzId") int clazzId,
            @RequestParam(name = "status") int status) {
        Attendance attendance = new Attendance();
        AttendanceResDto dto = new AttendanceResDto();
        try {
            Clazz c = clazzService.getClazzById(clazzId).orElse(null);
            attendance.setClazz(c);
            if (attendance.getClazz().getRequest().getSlots() <= (attendanceService.getAttendedByCId(clazzId) + 1)) {
                c.setStatus(2);
                clazzService.save(c);
            }
            attendance.setStatus(status);
            attendance.setDateCreate(new Date(System.currentTimeMillis()));

            dto.fromAttendance(attendanceService.save(attendance));
        } catch (Exception e) {
            return ApiResponseDto.<AttendanceResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<AttendanceResDto>builder().data(dto).build();
    }
}
