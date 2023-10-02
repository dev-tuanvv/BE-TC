package com.tutorcenter.model;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data
public class User {
    @Id
    private int id;
    private String email;
    private String password;
    private String fullname;
}
