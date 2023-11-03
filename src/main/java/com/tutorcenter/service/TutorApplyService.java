package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.TutorApply;

@Service
public interface TutorApplyService {
    List<TutorApply> findAll();

    Optional<TutorApply> getTutorApplyById(int id);

    List<TutorApply> getTutorAppliesById(List<Integer> idList);
}
