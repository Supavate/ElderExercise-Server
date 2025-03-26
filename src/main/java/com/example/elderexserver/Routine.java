package com.example.elderexserver;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int staff_id;
    @OneToMany(mappedBy = "routine")
    private Set<Routine_exercises> Exercises;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public Set<Routine_exercises> getExercises() {
        return Exercises;
    }

    public void setExercises(Set<Routine_exercises> exercises) {
        Exercises = exercises;
    }
}
