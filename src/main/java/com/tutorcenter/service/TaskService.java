package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Task;

@Service
public interface TaskService {
    int findBestSuitManagerId();

    List<Integer> findBestSuitManagerIds(int slTask);

    void autoAssignTask();

    Task save(Task task);

    List<Task> getAllTask();

    List<Task> getListTaskByManagerId(int mId);

    Optional<Task> getTaskById(int id);
}
