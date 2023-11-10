package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Attendance;

@Service
public interface AttendanceService {
    List<Attendance> findAll();

    Optional<Attendance> getAttendanceById(int id);

    List<Attendance> getAttendancesById(List<Integer> idList);

    List<Attendance> getAttendancesByClazzId(int cId);

    Attendance save(Attendance attendance);
}
