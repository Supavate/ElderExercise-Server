package com.example.elderexserver.data.staff;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Role {
    @Id
    private int id;
    private String name;
    private String description;
}
