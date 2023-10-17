package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Clazz;

@Repository
public interface ClazzRepository extends JpaRepository<Clazz, Integer> {

}
