package com.example.elderexserver.data.patient;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Status {
    @Id
    private int id;
    private String name;
    private String description;

}
