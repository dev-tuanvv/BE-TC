package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.TutorApply;

@Repository
public interface TutorApplyRepository extends JpaRepository<TutorApply, Integer> {

}
