package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.TutorSubject;

@Service
public interface TutorSubjectService {
    List<TutorSubject> findAll();

    List<TutorSubject> getTutorSubjectsById(List<Integer> idList);

    Optional<TutorSubject> getTutorSubjectById(int id);

    List<Integer> getListSIdByTId(int tId);

    TutorSubject save(TutorSubject tutorSubject);
}
