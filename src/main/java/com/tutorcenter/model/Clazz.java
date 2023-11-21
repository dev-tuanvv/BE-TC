package com.tutorcenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblClazz")

public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requestId", referencedColumnName = "id")
    private Request request;
    @JsonIgnore
    @OneToOne(mappedBy = "clazz")
    private Feedback feedback;
    @ManyToOne
    @JoinColumn(name = "tutorId")
    private Tutor tutor;
    @Column
    private int status;
    @Column
    private boolean isDeleted;
}
