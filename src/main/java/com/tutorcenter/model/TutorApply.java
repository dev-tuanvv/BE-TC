package com.tutorcenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "tblTutorApply")
public class TutorApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "classId")
    private Clazz clazz;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tutorId")
    private Tutor tutor;
    @Column
    private int status;
    @Column
    private boolean isDeleted;
}
