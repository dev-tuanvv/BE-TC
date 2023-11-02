package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

}
