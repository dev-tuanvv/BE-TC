package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.RequestSubject;

@Service
public interface RequestSubjectService {
    List<RequestSubject> findAll();

    List<RequestSubject> findAllByRequestRequestId(int requestId);

    List<RequestSubject> getRSubjectsById(List<Integer> idList);

    List<RequestSubject> getRSByRId(int rId);

    List<Integer> getListSIdByRId(int rId);

    Optional<RequestSubject> getRequestSubjectById(int id);

    RequestSubject createRSubject(int rId, int sId);

    void updateByRequestId(int id, List<Integer> subjects);

}
