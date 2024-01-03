package com.tutorcenter.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Question;
import com.tutorcenter.model.Subject;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findBySubject(Subject subject);

    @Query("SELECT q FROM Question q WHERE q.subject = :subject AND q.difficulty = :difficulty")
    List<Question> findBySubjectAndDifficulty(@Param("subject") Subject subject,
            @Param("difficulty") int difficulty);
}
