package com.tutorcenter.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tutorcenter.service.TaskService;

@Component
public class ScheduledAutoAssign {

    @Autowired
    private TaskService taskService;

    // This method will be scheduled to run periodically
    @Scheduled(cron = "0 0 0,12 * * ?") // Run at 0h and 12h every day
    // @Scheduled(fixedRate = 10000) // Run every 10 secs
    public void autoAssign() {
        taskService.autoAssignTask();
        System.out.println("Run auto assign task for all manager...");
    }
}
