package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Subject;
import com.tutorcenter.repository.SubjectRepository;
import com.tutorcenter.service.SubjectService;

@Component
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public List<Subject> getSubjectsByListId(List<Integer> sId) {
        return subjectRepository.findAllById(sId);
    }

    @Override
    public Optional<Subject> getSubjectById(int id) {
        return subjectRepository.findById(id);
    }

}
