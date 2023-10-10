package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Manager;

@Service
public interface ManagerService {
    List<Manager> findAll();
}
