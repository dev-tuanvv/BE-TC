package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Clazz;

@Service
public interface ClazzService {
    List<Clazz> findAll();
}
