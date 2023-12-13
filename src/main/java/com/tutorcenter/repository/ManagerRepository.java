package com.tutorcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    List<Manager> findByStatus(int status);
}
