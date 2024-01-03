package com.tutorcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Subject;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorSubject;

@Repository
public interface TutorSubjectRepository extends JpaRepository<TutorSubject, Integer> {
    @Query("SELECT ts FROM TutorSubject ts WHERE ts.subject = :subject AND ts.tutor = :tutor")
    List<TutorSubject> findBySubjectAndTutor(Subject subject, Tutor tutor);
}
