package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.TutorSubject;

@Repository
public interface TutorSubjectRepository extends JpaRepository<TutorSubject, Integer> {

}
