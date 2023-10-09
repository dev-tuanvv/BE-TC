package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {

}
