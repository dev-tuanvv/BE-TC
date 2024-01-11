package com.tutorcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorDistrict;

@Repository
public interface TutorDistrictRepository extends JpaRepository<TutorDistrict, Integer> {
    List<TutorDistrict> findByTutor(Tutor tutor);
}
