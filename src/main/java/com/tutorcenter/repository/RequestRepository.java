package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

}
