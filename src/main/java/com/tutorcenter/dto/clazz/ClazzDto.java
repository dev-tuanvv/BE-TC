package com.tutorcenter.dto.clazz;

import java.util.List;

import com.tutorcenter.model.Attendance;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Order;
import com.tutorcenter.model.TutorApply;

import lombok.Data;

@Data
public class ClazzDto {
    private int id;

    private int requestId;

    private int feedbackId;

    private List<Integer> orders;

    private List<Integer> tutorApplies;

    private List<Integer> attendances;

    private int tutorId;

    private String status;

    private boolean isDeleted;

    public void convertClazz(Clazz c) {
        this.id = c.getId();
        this.requestId = c.getRequest().getId();
        this.feedbackId = c.getFeedback().getId();
        for (Order o : c.getOrders()) {
            this.orders.add(o.getId());
        }
        for (TutorApply ta : c.getTutorApplies()) {
            this.tutorApplies.add(ta.getId());
        }
        for (Attendance a : c.getAttendances()) {
            this.attendances.add(a.getId());
        }
        this.tutorId = c.getTutor().getId();
        this.status = c.getStatus();
        this.isDeleted = c.isDeleted();
    }

    // request, feedback, orders, tutorApplies, attendances
    public void convertClazzDto(Clazz c) {
        c.setId(this.id);
        // c.setTutorId(this.tutorId);
        c.setDeleted(this.isDeleted);
    }
}
