package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

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
        return tutorRepository.findAll().stream().filter(b -> !b.isDeleted()).toList();
    }

    @Override
    public Optional<Tutor> getTutorById(int id) {
        return tutorRepository.findById(id);
    }

    @Override
    public List<Tutor> getTutorsById(List<Integer> idList) {
        return tutorRepository.findAllById(idList);
    }

    @Override
    public Tutor save(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

}
