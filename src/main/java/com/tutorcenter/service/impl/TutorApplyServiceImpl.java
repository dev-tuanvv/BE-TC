package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.TutorApply;
import com.tutorcenter.repository.TutorApplyRepository;
import com.tutorcenter.service.TutorApplyService;

@Component
public class TutorApplyServiceImpl implements TutorApplyService {

    @Autowired
    TutorApplyRepository tutorApplyRepository;

    @Override
    public List<TutorApply> findAll() {
        return tutorApplyRepository.findAll();
    }

    @Override
    public Optional<TutorApply> getTutorApplyById(int id) {
        return tutorApplyRepository.findById(id);
    }

    @Override
    public List<TutorApply> getTutorAppliesById(List<Integer> idList) {
        return tutorApplyRepository.findAllById(idList);
    }

}
