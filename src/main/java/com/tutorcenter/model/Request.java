package com.tutorcenter.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblRequest")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "parentID")
    private Parent parent;
    @ManyToOne
    @JoinColumn(name = "managerID")
    private Manager manager;
    @Column
    private String phone;
    @Column
    private String address;
    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;
    @Column
    private int slots;
    @Column
    private int slotsLength;
    @Column
    private float tuition;
    @Column
    private String notes;
    @Column
    private Date dateStart;
    @Column
    private Date dateEnd;
    @Column
    private Date dateCreate;
    @Column
    private Date dateModified;
    @Column
    private String status;
    @Column
    private String rejectReason;
}
