package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Log;

@Service
public interface LogService {
    List<Log> findAll();
}
