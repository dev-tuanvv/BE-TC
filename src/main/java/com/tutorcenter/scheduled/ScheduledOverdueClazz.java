package com.tutorcenter.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Clazz;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.NotificationService;
import com.tutorcenter.service.SystemVariableService;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledOverdueClazz {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private SystemVariableService systemVariableService;
    @Autowired
    private NotificationService notificationService;

    // This method will be scheduled to run periodically
    @Scheduled(cron = "0 0 0 * * ?") // Run at 0h every day
    public void updateStatusToOverdue() {
        List<Clazz> clazzs = clazzService.findAll();

        Date currentTime = new Date();

        for (Clazz c : clazzs) {
            Date creationDate = c.getRequest().getDateCreate();
            int overdue_clazz = Integer.parseInt(systemVariableService.getSysVarByVarKey("clazz_overdue").getValue());
            // Assume "overdue" status should be set after overdue_clazz days
            long overdueThresholdMillis = creationDate.getTime() + (overdue_clazz * 24 * 60 * 60 * 1000);

            if (currentTime.after(new Date(overdueThresholdMillis)) && (c.getStatus() == 0)) {
                c.setStatus(4);
                // send notifications
                notificationService.add(c.getRequest().getParent(),
                        "Lớp của bạn đã chuyển sang trạng thái quá hạn sau " + overdue_clazz + " ngày không hoạt động");
            }
        }
    }
}
