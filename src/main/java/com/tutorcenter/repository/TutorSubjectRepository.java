package com.tutorcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorSubject;

@Repository
public interface TutorSubjectRepository extends JpaRepository<TutorSubject, Integer> {
    List<TutorSubject> findByTutor(Tutor tutor);
}
