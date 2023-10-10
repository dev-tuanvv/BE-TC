package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Tutor;

@Service
public interface TutorService {
    List<Tutor> findAll();
}
