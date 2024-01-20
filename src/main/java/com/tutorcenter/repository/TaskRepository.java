package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

  @Query(value = "SELECT COUNT(*) FROM tbl_task t WHERE t.manager_id =:managerId and t.status = 1", nativeQuery = true)
  public int countByManager(int managerId);

  List<Task> findByStatus(int status);

  List<Task> findByManager(Manager manager);

  List<Task> findByRequestId(int rId);
}
