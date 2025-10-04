package com.example.elderexserver.data.patient;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Gender {
    @Id
    private int id;
    private String name;
}
