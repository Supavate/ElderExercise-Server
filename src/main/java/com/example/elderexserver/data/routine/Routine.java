package com.example.elderexserver.data.routine;

import com.example.elderexserver.data.staff.Staff;
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

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Routine_exercises> Exercises;

    public Routine() {}

    public Routine(String name, String description, Staff staff) {
        this.name = name;
        this.description = description;
        this.staff = staff;
    }

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
