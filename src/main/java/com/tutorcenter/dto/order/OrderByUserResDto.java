package com.tutorcenter.dto.order;

import java.sql.Date;
import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Order;

import lombok.Data;

@Data
public class OrderByUserResDto {
    private int id;
    private int classId;
    private String parentName;
    private String phone;
    private List<SubjectLevelResDto> subjects;
    private int slots;
    private float tuition;
    private String address;
    private String districtName;
    private String provinceName;
    private String gender;
    private String nameTutor;
    private Date time;
    private double amount;
    private int type;

    public void fromOrder(Order order) {
        this.id = order.getId();
        this.classId = order.getClazz().getId();
        this.parentName = order.getClazz().getRequest().getParent().getFullname();
        this.phone = order.getClazz().getRequest().getPhone();
        this.slots = order.getClazz().getRequest().getSlots();
        this.tuition = order.getClazz().getRequest().getTuition();
        this.address = order.getClazz().getRequest().getAddress();
        this.districtName = order.getClazz().getRequest().getDistrict().getName();
        this.provinceName = order.getClazz().getRequest().getDistrict().getProvince().getName();
        this.gender = order.getClazz().getRequest().getGender();
        this.nameTutor = order.getClazz().getTutor().getFullname();
        this.time = order.getTimeCreate();
        this.amount = order.getAmount();
        this.type = order.getType();
    }
}
