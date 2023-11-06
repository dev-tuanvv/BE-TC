package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Request;

@Service
public interface RequestService {
    List<Request> findAll();

    Request save(Request request);

    Optional<Request> getRequestById(int id);

    List<Request> getRequestByParentID(int pId);

    List<Request> getRequestByManagerID(int pId);
}
