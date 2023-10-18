package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {

}
