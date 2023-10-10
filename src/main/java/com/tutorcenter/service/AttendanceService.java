package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Attendance;

@Service
public interface AttendanceService {
    List<Attendance> findAll();
}
