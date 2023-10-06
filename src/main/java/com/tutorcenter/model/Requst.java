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
public class Requst {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "parentsID")
    private Parents parents;
    @ManyToOne
    @JoinColumn(name = "managerID")
    private Manager manager;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private String district;
    @Column
    private String province;
    @Column
    private float grade;
    @Column
    private String subject;
    @Column
    private int amountStudent;
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
    private Date deatemodified;
    @Column
    private String status;
    @Column
    private String rejectReason;
}
