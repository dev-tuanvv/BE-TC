package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<TutorSubject> getTutorSubjectsById(List<Integer> idList) {
        return tutorSubjectRepository.findAllById(idList);
    }

    @Override
    public List<Integer> getListSIdByTId(int tId) {
        List<TutorSubject> list = tutorSubjectRepository.findAll().stream().filter(ts -> ts.getTutor().getId() == tId)
                .collect(Collectors.toList());
        return list.stream().map(ts -> ts.getSubject().getId()).collect(Collectors.toList());
    }

    @Override
    public TutorSubject save(TutorSubject tutorSubject) {
        return tutorSubjectRepository.save(tutorSubject);
    }

}
