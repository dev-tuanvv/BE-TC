package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.TutorApply;

@Service
public interface TutorApplyService {
    List<TutorApply> findAll();
}
