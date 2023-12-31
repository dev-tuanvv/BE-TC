package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Manager;
import com.tutorcenter.repository.ManagerRepository;
import com.tutorcenter.service.ManagerService;

@Component
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerRepository managerRepository;

    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public Optional<Manager> getManagerById(int id) {
        return managerRepository.findById(id);
    }

}
