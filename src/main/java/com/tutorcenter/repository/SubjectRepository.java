package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}
