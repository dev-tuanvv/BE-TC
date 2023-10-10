package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Log;
import com.tutorcenter.repository.LogRepository;
import com.tutorcenter.service.LogService;

@Component
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

}
