package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Parent;
import com.tutorcenter.repository.ParentRepository;
import com.tutorcenter.service.ParentService;

@Component
public class ParentServiceImpl implements ParentService {

    @Autowired
    ParentRepository parentsRepository;

    @Override
    public List<Parent> findAll() {
        return parentsRepository.findAll();
    }

}
