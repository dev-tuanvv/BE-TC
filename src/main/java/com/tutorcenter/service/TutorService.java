package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Tutor;

@Service
public interface TutorService {
    List<Tutor> findAll();

    Optional<Tutor> getTutorById(int id);

    List<Tutor> getTutorsById(List<Integer> idList);
}
