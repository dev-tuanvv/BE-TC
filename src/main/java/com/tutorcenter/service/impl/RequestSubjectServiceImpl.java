package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<RequestSubject> getRSubjectByRId(int rId) {
        List<RequestSubject> requestSubjects = new ArrayList<>();
        for (RequestSubject rs : findAll()) {
            if (rs.getRequest().getId() == rId)
                requestSubjects.add(rs);
        }
        return requestSubjects;
    }

    @Override
    public void updateByRequestId(int rId, List<Integer> subjects) {
        for (RequestSubject rs : getRSubjectByRId(rId)) {
            requestSubjectRepository.deleteById(rs.getId());
        }
        for (Integer sId : subjects) {
            createRSubject(rId, sId);
        }
    }

    @Override
    public List<RequestSubject> findAllByRequestRequestId(int requestId) {
        return requestSubjectRepository.findByRequest_Id(requestId);
    }

    @Override
    public List<Integer> getListSIdByListRSId(List<RequestSubject> listRS) {

        return listRS.stream().map(rs -> rs.getSubject().getId()).collect(Collectors.toList());
    }
}
