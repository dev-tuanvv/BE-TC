package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Parents;

@Repository
public interface ParentsRepository extends JpaRepository<Parents, Integer> {

}
