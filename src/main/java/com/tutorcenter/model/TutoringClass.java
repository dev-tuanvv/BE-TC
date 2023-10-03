package com.tutorcenter.model;

import org.hibernate.annotations.ForeignKey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblTutoringClass")
public class TutoringClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int requestID;
    @Column
    private int tutorID;
    @Column
    private String status;
}
