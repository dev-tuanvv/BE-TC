package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.TutoringClass;

@Service
public interface TutoringClassService {
    List<TutoringClass> findAll();
}
