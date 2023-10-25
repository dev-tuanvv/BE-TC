package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Request;

@Service
public interface RequestService {
    List<Request> findAll();

    Request save(Request request);

    void disable(int id);

    Optional<Request> getRequestById(int id);
}
