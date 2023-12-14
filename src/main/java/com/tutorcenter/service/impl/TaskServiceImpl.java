package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.RequestVerification;
import com.tutorcenter.model.Task;
import com.tutorcenter.repository.TaskRepository;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.RequestVerificationService;
import com.tutorcenter.service.TaskService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestVerificationService requestVerificationService;

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
        if (slTask > managerTasks.size()) {
            slTask = managerTasks.size();
        }
        for (ManagerTask mt : managerTasks.subList(0, slTask)) {
            managerIds.add(mt.getManagerId());
        }
        return managerIds;
    }

    public void autoAssignTask() {
        List<Task> tasks = taskRepository.findByStatus(0);
        List<Manager> managers = managerService.findAllActive();
        int j = 0;

        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setManager(managers.get(j));
            tasks.get(i).setStatus(1);

            // taskRepository.save(tasks.get(i));
            if (tasks.get(i).getType() == 1) {
                Request request = requestService.getRequestById(tasks.get(i).getRequestId()).orElse(null);
                request.setManager((managers.get(j)));
                requestService.save(request);
            } else {
                RequestVerification requestVerification = requestVerificationService
                        .getRVById(tasks.get(i).getRequestId()).orElse(null);
                requestVerification.setManager((managers.get(j)));
                requestVerificationService.save(requestVerification);
            }
            j++;
            if (j >= managers.size()) {
                j = 0;
            }
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
