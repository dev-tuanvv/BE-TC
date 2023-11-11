package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.RequestVerification;

@Service
public interface RequestVerificationService {
    List<RequestVerification> findAll();

    Optional<RequestVerification> getRVById(int id);

    RequestVerification save(RequestVerification requestVerification);

    List<RequestVerification> getRVByTutorId(int tId);

    List<RequestVerification> getRVByManagerId(int mId);
}
