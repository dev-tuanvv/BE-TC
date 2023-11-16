package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Optional<RequestVerification> getRVById(int id) {
        return requestVerificationRepository.findById(id);
    }

    @Override
    public RequestVerification save(RequestVerification requestVerification) {
        return requestVerificationRepository.save(requestVerification);
    }

    @Override
    public List<RequestVerification> getRVByTutorId(int tId) {
        List<RequestVerification> list = requestVerificationRepository.findAll().stream()
                .filter(rv -> rv.getTutor().getId() == tId).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<RequestVerification> getRVByManagerId(int mId) {
        List<RequestVerification> list = new ArrayList<>();
        for (RequestVerification rv : requestVerificationRepository.findAll()) {
            if (rv.getManager() != null && rv.getManager().getId() == mId)
                list.add(rv);
        }
        return list;
    }

}
