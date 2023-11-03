package com.tutorcenter.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblClass")

public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "requestId", referencedColumnName = "id")
    private Request request;
    @OneToOne(mappedBy = "clazz")
    private Feedback feedback;
    @Column
    @OneToMany(mappedBy = "clazz")
    private List<Order> orders;
    @Column
    @OneToMany(mappedBy = "clazz")
    private List<TutorApply> tutorApplies;
    @Column
    @OneToMany(mappedBy = "clazz")
    private List<Attendance> attendances;
    @Column
    private int tutorId;
    @Column
    private String status;
    @Column
    private boolean isDeleted;
}
