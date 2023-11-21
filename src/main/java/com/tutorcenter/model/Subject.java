package com.tutorcenter.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblSubject")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String level;
    @Column
    private String details; // thong tin chi tiet ve subject
    @Column
    private float pricePerHour; // help reccomend fee
    @Column
    private int status; // co tutor, ko co tutor, dang km,...
}
