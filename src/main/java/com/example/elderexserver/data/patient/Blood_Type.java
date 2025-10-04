package com.example.elderexserver.data.patient;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Blood_Type {
    @Id
    private int id;
    private String type;
}
