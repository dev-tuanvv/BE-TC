package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.SystemVariable;

@Service
public interface SystemVariableService {
    List<SystemVariable> findAll();

    SystemVariable getSysVarByVarKey(String varKey);

    SystemVariable save(SystemVariable systemVariable);
}
