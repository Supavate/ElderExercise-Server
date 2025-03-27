package com.example.elderexserver.exercise;

import com.example.elderexserver.staff.Staff;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @ManyToOne
    private Staff staff;

    @OneToMany(mappedBy = "routine")
    @JsonManagedReference
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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Set<Routine_exercises> getExercises() {
        return Exercises;
    }

    public void setExercises(Set<Routine_exercises> exercises) {
        Exercises = exercises;
    }
}
