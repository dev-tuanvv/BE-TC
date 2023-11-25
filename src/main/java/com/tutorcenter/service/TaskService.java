package com.tutorcenter.service;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Task;

@Service
public interface TaskService {
 int findBestSuitManagerId();
 Task save(Task task);
}
