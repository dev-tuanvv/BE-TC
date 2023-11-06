package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Attendance;
import com.tutorcenter.repository.AttendanceRepository;
import com.tutorcenter.service.AttendanceService;

@Component
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }

    @Override
    public Optional<Attendance> getAttendanceNyId(int id) {
        return attendanceRepository.findById(id);
    }

    @Override
    public List<Attendance> getAttendancesById(List<Integer> idList) {
        return attendanceRepository.findAllById(idList);
    }

}
