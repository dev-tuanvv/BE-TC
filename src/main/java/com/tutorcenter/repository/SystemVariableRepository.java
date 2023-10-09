package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.SystemVariable;

@Repository
public interface SystemVariableRepository extends JpaRepository<SystemVariable, Integer> {

}
