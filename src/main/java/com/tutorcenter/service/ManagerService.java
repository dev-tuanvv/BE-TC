package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Manager;

@Service
public interface ManagerService {
    List<Manager> findAll();

    Optional<Manager> getManagerById(int id);

    Manager save(Manager manager);
}
