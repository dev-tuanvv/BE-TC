package com.tutorcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.RequestSubject;

@Repository
public interface RequestSubjectRepository extends JpaRepository<RequestSubject, Integer> {
    List<RequestSubject> findByRequest_Id(int requestId);
}
