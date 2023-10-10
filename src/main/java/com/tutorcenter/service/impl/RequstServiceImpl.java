package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Requst;
import com.tutorcenter.repository.RequstRepository;
import com.tutorcenter.service.RequstService;

@Component
public class RequstServiceImpl implements RequstService {

    @Autowired
    RequstRepository requstRepository;

    @Override
    public List<Requst> findAll() {
        return requstRepository.findAll();
    }

}
