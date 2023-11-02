package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

}
