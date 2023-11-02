package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.TutorSubject;
import com.tutorcenter.repository.TutorSubjectRepository;
import com.tutorcenter.service.TutorSubjectService;

@Component
public class TutorSubjectServiceImpl implements TutorSubjectService {
    @Autowired
    TutorSubjectRepository tutorSubjectRepository;

    @Override
    public List<TutorSubject> findAll() {
        return tutorSubjectRepository.findAll();
    }

    @Override
    public Optional<TutorSubject> getTutorSubjectById(int id) {
        return tutorSubjectRepository.findById(id);
    }

    @Override
    public List<TutorSubject> gTutorSubjectsById(List<Integer> idList) {
        return tutorSubjectRepository.findAllById(idList);
    }

}
