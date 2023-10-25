package com.tutorcenter.service.impl;

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
        return requestRepository.findAll();
    }

    @Override
    public Request save(Request request) {
        return requestRepository.saveAndFlush(request);
    }

    @Override
    public void disable(int id) {

    }

    @Override
    public Optional<Request> getRequestById(int id) {
        return requestRepository.findById(id);
    }

}
