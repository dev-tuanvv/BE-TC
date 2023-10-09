package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.RequestVerification;

@Repository
public interface RequestVerificationRepository extends JpaRepository<RequestVerification, Integer> {

}
