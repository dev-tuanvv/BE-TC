package com.tutorcenter.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Clazz;
import com.tutorcenter.service.AttendanceService;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.EmailService;
import com.tutorcenter.service.NotificationService;
import com.tutorcenter.service.SystemVariableService;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledStatusClazz {

        @Autowired
        private ClazzService clazzService;
        @Autowired
        private AttendanceService attendanceService;
        @Autowired
        private NotificationService notificationService;
        @Autowired
        private SystemVariableService systemVariableService;
        @Autowired
        private EmailService emailService;

        // This method will be scheduled to run periodically
        @Scheduled(cron = "0 0 0 * * ?") // Run at 0h every day
        public void updateStatusToOverdue() {
                // status 7 = Wait for feedback
                List<Clazz> clazzs = clazzService.getClazzByStatus(7);
                int overdue_clazz = Integer
                                .parseInt(systemVariableService.getSysVarByVarKey("clazz_overdue").getValue());
                int date_wait_for_feedback = Integer
                                .parseInt(systemVariableService.getSysVarByVarKey("date_wait_for_feedback").getValue());
                Date currentTime = new Date();

                for (Clazz c : clazzs) {
                        // get date create of last attendance
                        Date lastDate = attendanceService.getAttendancesByClazzId(c.getId())
                                        .get(attendanceService.getAttendancesByClazzId(c.getId()).size() - 1)
                                        .getDateCreate();
                        // status = 2 should be set after date_wait_for_feedback days
                        long overdueThresholdMillis = lastDate.getTime()
                                        + (date_wait_for_feedback * 24 * 60 * 60 * 1000);

                        if ((currentTime.after(new Date(overdueThresholdMillis)) && (c.getStatus() == 7))) {
                                c.setStatus(2); // clazz end
                                // send notifications
                                notificationService.add(c.getRequest().getParent(),
                                                "Lớp " + c.getId()
                                                                + " của bạn đã tự động chuyển sang trạng thái kết thúc và tiền sẽ được trả cho gia sư sau 7 ngày không đánh giá");
                                emailService.sendEmail(c.getRequest().getManager().getEmail(), "Lớp đã có thể thao tác",
                                                "Lớp " + c.getId() + " đã tự động chuyển trạng thái sau "
                                                                + date_wait_for_feedback
                                                                + " ngày không có feedback, đã có thể thực hiện chuyển tiền cho gia sư");
                        } // check status = 8 wait for consider
                        else if ((currentTime.after(new Date(c.getRequest().getDateEnd().getTime()))
                                        && (c.getStatus() == 1))) {
                                c.setStatus(8); // wait for consider
                                // send notifications
                                notificationService.add(c.getRequest().getParent(),
                                                "Lớp " + c.getId()
                                                                + " của bạn đã tự động chuyển sang trạng thái kết thúc tạm thời vì đã qua thời gian kết thúc khi được đăng ký");
                        } // check class end date before now or after 60 days when status = 0 not start
                        else if ((currentTime
                                        .after(new Date(c.getRequest().getDateCreate().getTime()
                                                        + (overdue_clazz * 24 * 60 * 60 * 1000)))
                                        && (c.getStatus() == 0))
                                        || currentTime.after(c.getRequest().getDateStart()) && (c.getStatus() == 0)) {
                                c.setStatus(4); // clazz overdue
                                // send notifications
                                notificationService.add(c.getRequest().getParent(),
                                                "Lớp " + c.getId()
                                                                + " của bạn đã tự động chuyển sang trạng thái quá hạn vì đã qua ngày bắt đầu dạy hoặc sau "
                                                                + overdue_clazz
                                                                + " ngày không đổi trạng thái");
                        }
                }
        }
}
