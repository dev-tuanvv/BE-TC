package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Feedback;
import com.tutorcenter.model.SystemVariable;
import com.tutorcenter.repository.SystemVariableRepository;
import com.tutorcenter.service.SystemVariableService;

@Component
public class SystemVariableServiceImpl implements SystemVariableService {

    @Autowired
    SystemVariableRepository systemVariableRepository;

    @Override
    public List<SystemVariable> findAll() {
        return systemVariableRepository.findAll();
    }

    @Override
    public SystemVariable getSysVarByVarKey(String varKey) {
        for (SystemVariable sv : systemVariableRepository.findAll()) {
            if (sv.getVarKey().equals(varKey))
                return sv;
        }
        return null;
    }

    @Override
    public SystemVariable save(SystemVariable systemVariable) {
        return systemVariableRepository.save(systemVariable);
    }

}
