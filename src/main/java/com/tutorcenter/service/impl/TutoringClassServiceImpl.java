package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.TutoringClass;
import com.tutorcenter.repository.TutoringClassRepository;
import com.tutorcenter.service.TutoringClassService;

@Component
public class TutoringClassServiceImpl implements TutoringClassService {

    @Autowired
    TutoringClassRepository tutoringClassRepository;

    @Override
    public List<TutoringClass> findAll() {
        return tutoringClassRepository.findAll();
    }

}
