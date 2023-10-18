package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Parent;

@Service
public interface ParentService {
    List<Parent> findAll();
}
