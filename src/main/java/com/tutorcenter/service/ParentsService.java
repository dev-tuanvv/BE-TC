package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Parents;

@Service
public interface ParentsService {
    List<Parents> findAll();
}
