package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Request;
import com.tutorcenter.repository.RequestRepository;
import com.tutorcenter.service.RequestService;

@Component
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll().stream().filter(b -> !b.isDeleted()).toList();
    }

    @Override
    public Request save(Request request) {
        return requestRepository.saveAndFlush(request);
    }

    @Override
    public Optional<Request> getRequestById(int id) {
        return requestRepository.findById(id);
    }

    @Override
    public List<Request> getRequestByParentID(int pId) {
        List<Request> list = new ArrayList<>();
        for (Request r : findAll()) {
            if (r.getParent().getId() == pId)
                list.add(r);
        }
        return list;
    }

    @Override
    public List<Request> getRequestByManagerID(int mId) {
        List<Request> list = new ArrayList<>();
        for (Request r : findAll()) {
            if (r.getManager() != null && r.getManager().getId() == mId)
                list.add(r);
        }
        return list;
    }

}
