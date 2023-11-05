package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.RequestSubject;

@Service
public interface RequestSubjectService {
    List<RequestSubject> findAll();

    List<RequestSubject> getRSubjectsById(List<Integer> idList);

    List<RequestSubject> getRSubjectByRId(int rId);

    Optional<RequestSubject> getRequestSubjectById(int id);

    RequestSubject createRSubject(int rId, int sId);

    void updateByRequestId(int id, List<Integer> subjects);
}
