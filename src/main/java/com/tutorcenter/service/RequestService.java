package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Request;

@Service
public interface RequestService {
    List<Request> findAll();

    void save(Request request);
}
