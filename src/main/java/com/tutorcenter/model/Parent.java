package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "tblParent")
@Data
@EqualsAndHashCode(callSuper = true)
public class Parent extends User {
    @Column
    private String phone;
    @Column
    private String address;
    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;
    @Column
    private int status;
}
