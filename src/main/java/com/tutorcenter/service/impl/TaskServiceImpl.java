package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.RequestVerification;
import com.tutorcenter.model.Task;
import com.tutorcenter.repository.TaskRepository;
import com.tutorcenter.service.EmailService;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.RequestVerificationService;
import com.tutorcenter.service.SystemVariableService;
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
    @Autowired
    private EmailService emailService;
    @Autowired
    private SystemVariableService systemVariableService;

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
        HashMap<String, List<String>> taskLink = new HashMap<>();

        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setManager(managers.get(j));
            tasks.get(i).setStatus(1);

            // taskRepository.save(tasks.get(i));
            if (tasks.get(i).getType() == 1) {
                Request request = requestService.getRequestById(tasks.get(i).getRequestId()).orElse(null);
                request.setManager((managers.get(j)));
                requestService.save(request);
                // add task link
                taskLink.putIfAbsent(tasks.get(i).getManager().getEmail(), new ArrayList<>());
                List<String> links = taskLink.get(tasks.get(i).getManager().getEmail());
                links.add(" Request " + tasks.get(i).getRequestId());
            } else {
                RequestVerification requestVerification = requestVerificationService
                        .getRVById(tasks.get(i).getRequestId()).orElse(null);
                requestVerification.setManager((managers.get(j)));
                requestVerificationService.save(requestVerification);
                // add task link
                taskLink.putIfAbsent(tasks.get(i).getManager().getEmail(), new ArrayList<>());
                List<String> links = taskLink.get(tasks.get(i).getManager().getEmail());
                links.add(" Request Verification " + tasks.get(i).getRequestId());
            }
            j++;
            if (j >= managers.size()) {
                j = 0;
            }
        }
        taskRepository.saveAll(tasks);
        for (Map.Entry<String, List<String>> entry : taskLink.entrySet()) {
            List<String> links = entry.getValue();
            String content = "Bạn đã được assign các task: ";
            int count = 1;
            for (String link : links) {
                content += (count + "." + link);
                count++;
            }
            emailService.sendEmail(entry.getKey(), "Task mới đã được assign", content);
        }
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

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getListTaskByManagerId(int mId) {
        List<Task> response = new ArrayList<>();
        for (Task t : taskRepository.findAll()) {
            if (t.getManager() != null) {
                if (t.getManager().getId() == mId) {
                    response.add(t);
                }
            }
        }
        return response;
    }

    @Override
    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task getTaskByRequest(int rId, int type) {
        Task task = new Task();
        for (Task t : taskRepository.findByRequestId(rId)) {
            if (t.getType() == type) {
                task = t;
            }
        }
        return task;
    }

    @Override
    public String finish(int rId, int type) {
        Task task = getTaskByRequest(rId, type);

        if (task.getManager().getId() != Common.getCurrentUserId()) {
            return "Bạn không được assign cho task này";
        }
        if (task.getStatus() == 1) {
            int task_work_time = Integer
                    .parseInt(systemVariableService.getSysVarByVarKey("task_work_time").getValue());
            Date now = new Date(System.currentTimeMillis());
            if ((now.getTime() - task.getDateCreate().getTime()) > task_work_time * 24 * 60 * 60 * 1000) {
                task.setStatus(3); // finish after deadlin
                task.setDateFinished(now);
            } else {
                task.setStatus(2); // finish on time
                task.setDateFinished(now);
            }
        }
        taskRepository.save(task);
        return "Hoàn thành task";
    }
}
