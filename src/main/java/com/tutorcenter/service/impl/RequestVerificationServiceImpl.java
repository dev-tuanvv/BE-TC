package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.RequestVerification;
import com.tutorcenter.repository.RequestVerificationRepository;
import com.tutorcenter.service.RequestVerificationService;

@Component
public class RequestVerificationServiceImpl implements RequestVerificationService {

    @Autowired
    RequestVerificationRepository requestVerificationRepository;

    @Override
    public List<RequestVerification> findAll() {
        return requestVerificationRepository.findAll();
    }

}
