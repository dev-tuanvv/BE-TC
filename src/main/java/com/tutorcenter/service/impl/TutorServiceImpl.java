package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Tutor;
import com.tutorcenter.repository.TutorRepository;
import com.tutorcenter.service.TutorService;

@Component
public class TutorServiceImpl implements TutorService {

    @Autowired
    TutorRepository tutorRepository;

    @Override
    public List<Tutor> findAll() {
        return tutorRepository.findAll();
    }

}
