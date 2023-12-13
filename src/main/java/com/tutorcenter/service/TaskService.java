package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Task;

@Service
public interface TaskService {
    int findBestSuitManagerId();

    List<Integer> findBestSuitManagerIds(int slTask);

    void autoAssignTask();

    Task save(Task task);
}
