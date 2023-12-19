package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Parent;
import com.tutorcenter.repository.ParentRepository;
import com.tutorcenter.service.ParentService;

@Component
public class ParentsServiceImpl implements ParentService {

    @Autowired
    ParentRepository parentsRepository;

    @Override
    public List<Parent> findAll() {
        return parentsRepository.findAll().stream().filter(b -> !b.isDeleted()).toList();
    }

    @Override
    public Optional<Parent> getParentById(int id) {
        return parentsRepository.findById(id);
    }

}
