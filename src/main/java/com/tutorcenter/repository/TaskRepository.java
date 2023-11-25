package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorcenter.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

  @Query(value = "SELECT COUNT(*) FROM tbl_task t WHERE t.managerId =: managerId AND t.status = 0", nativeQuery = true)
  public int countByManager(int managerId);
}
