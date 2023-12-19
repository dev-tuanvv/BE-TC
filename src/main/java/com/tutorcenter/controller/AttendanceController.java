package com.tutorcenter.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.tutorcenter.service.NotificationService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private NotificationService notificationService;

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

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
        logger.info("Get list attendance");
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
        logger.info("Get attendance by class Id");
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
            if (c.getStatus() != 1) {// class start paid can take attend
                return ApiResponseDto.<AttendanceResDto>builder().message("Lớp không thể tạo điểm danh cho lớp này")
                        .build();
            }
            if (attendance.getClazz().getRequest().getSlots() <= (attendanceService.getAttendedByCId(clazzId) + 1)) {
                // status 7 = Wait for feedback
                c.setStatus(7);
                clazzService.save(c);
            }
            attendance.setStatus(status);
            attendance.setDateCreate(new Date(System.currentTimeMillis()));
            notificationService.add(c.getTutor(),
                    "Phụ huynh " + c.getRequest().getParent().getFullname()
                            + "đã tạo điểm danh lớp " + c.getId() + " thành công");
            notificationService.add(c.getRequest().getParent(),
                    "Phụ huynh " + c.getRequest().getParent().getFullname()
                            + "đã tạo điểm danh lớp " + c.getId() + " thành công");
            dto.fromAttendance(attendanceService.save(attendance));
        } catch (Exception e) {
            return ApiResponseDto.<AttendanceResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        logger.info("Create attendance");
        return ApiResponseDto.<AttendanceResDto>builder().data(dto).build();
    }
}
