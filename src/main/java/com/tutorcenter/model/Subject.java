package com.tutorcenter.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblSubject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String level;
    @Column
    @OneToMany(mappedBy = "subject")
    private List<TutorSubject> tSubjects;
    @Column
    @OneToMany(mappedBy = "subject")
    private List<RequestSubject> rSubjects;
    @Column
    private String details; // thong tin chi tiet ve subject
    @Column
    private float pricePerHour; // help reccomend fee
    @Column
    private String status; // co tutor, ko co tutor, dang km,...
}
