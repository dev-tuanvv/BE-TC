package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.RequestSubject;
import com.tutorcenter.repository.RequestSubjectRepository;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.RequestSubjectService;
import com.tutorcenter.service.SubjectService;

@Component
public class RequestSubjectServiceImpl implements RequestSubjectService {
    @Autowired
    RequestSubjectRepository requestSubjectRepository;
    @Autowired
    RequestService requestService;
    @Autowired
    SubjectService subjectService;

    @Override
    public List<RequestSubject> findAll() {
        return requestSubjectRepository.findAll();
    }

    @Override
    public Optional<RequestSubject> getRequestSubjectById(int id) {
        return requestSubjectRepository.findById(id);
    }

    @Override
    public List<RequestSubject> getRSubjectsById(List<Integer> idList) {
        return requestSubjectRepository.findAllById(idList);
    }

    @Override
    public RequestSubject createRSubject(int rId, int sId) {
        RequestSubject rSubject = new RequestSubject();
        rSubject.setRequest(requestService.getRequestById(rId).orElseThrow());
        rSubject.setSubject(subjectService.getSubjectById(sId).orElseThrow());
        return requestSubjectRepository.save(rSubject);
    }
}
