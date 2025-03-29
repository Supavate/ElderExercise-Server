package com.example.elderexserver.data.patient;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Blood_Type {
    @Id
    private int id;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }
}
