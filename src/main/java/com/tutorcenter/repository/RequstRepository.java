package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Requst;

@Repository
public interface RequstRepository extends JpaRepository<Requst, Integer> {

}
