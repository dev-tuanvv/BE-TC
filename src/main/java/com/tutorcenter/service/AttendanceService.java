package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Attendance;

@Service
public interface AttendanceService {
    List<Attendance> findAll();

    Optional<Attendance> getAttendanceNyId(int id);

    List<Attendance> getAttendancesById(List<Integer> idList);
}
