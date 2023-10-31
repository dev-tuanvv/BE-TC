package com.tutorcenter.model;

import java.util.Set;

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
    private Set<Order> orders;
    @Column
    @OneToMany(mappedBy = "clazz")
    private Set<TutorApply> tutorApplies;
    @Column
    @OneToMany(mappedBy = "clazz")
    private Set<Attendance> attendances;
    @Column
    private int tutorID;
    @Column
    private String status;
    @Column
    private boolean isDeleted;
}
