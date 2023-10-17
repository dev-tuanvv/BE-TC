package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Clazz;
import com.tutorcenter.repository.ClazzRepository;
import com.tutorcenter.service.ClazzService;

@Component
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    ClazzRepository ClazzRepository;

    @Override
    public List<Clazz> findAll() {
        return ClazzRepository.findAll();
    }

}
