package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Task;
import com.tutorcenter.repository.TaskRepository;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.TaskService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    private ManagerService managerService;

    public int findBestSuitManagerId() {
        List<Manager> managers = managerService.findAll();
        List<ManagerTask> managerTasks = new ArrayList<>();
        for (Manager manager : managers) {
            ManagerTask mt = new ManagerTask(manager.getId(), taskRepository.countByManager(manager.getId()));
            managerTasks.add(mt);
        }
        Collections.sort(managerTasks);
        return managerTasks.get(0).getManagerId();
    }

    public List<Integer> findBestSuitManagerIds(int slTask) {
        List<Manager> managers = managerService.findAll();
        List<ManagerTask> managerTasks = new ArrayList<>();
        for (Manager manager : managers) {
            ManagerTask mt = new ManagerTask(manager.getId(), taskRepository.countByManager(manager.getId()));
            managerTasks.add(mt);
        }
        Collections.sort(managerTasks);
        List<Integer> managerIds = new ArrayList<>();
        for (ManagerTask mt : managerTasks.subList(0, slTask)) {
            managerIds.add(mt.getManagerId());
        }
        return managerIds;
    }

    public void autoAssignTask() {
        List<Task> tasks = taskRepository.findByStatus(0);
        List<Integer> managerIds = findBestSuitManagerIds(tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setManager(managerService.getManagerById(managerIds.get(i)).orElse(null));
            tasks.get(i).setStatus(1);
        }
        taskRepository.saveAll(tasks);
    }

    @Data
    @AllArgsConstructor
    class ManagerTask implements Comparable<ManagerTask> {
        private int managerId;
        private Integer tasks = 0;

        @Override
        public int compareTo(ManagerTask o) {
            return tasks.compareTo(o.tasks);
        }
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }
}
