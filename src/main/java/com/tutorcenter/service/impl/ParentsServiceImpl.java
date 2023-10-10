package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Parents;
import com.tutorcenter.repository.ParentsRepository;
import com.tutorcenter.service.ParentsService;

@Component
public class ParentsServiceImpl implements ParentsService {

    @Autowired
    ParentsRepository parentsRepository;

    @Override
    public List<Parents> findAll() {
        return parentsRepository.findAll();
    }

}
