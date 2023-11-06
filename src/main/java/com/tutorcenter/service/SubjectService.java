package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Subject;

@Service
public interface SubjectService {
    List<Subject> findAll();

    List<Subject> getSubjectsByListId(List<Integer> sId);

    Optional<Subject> getSubjectById(int id);
}
