package com.example.elderexserver.data.routine;

import com.example.elderexserver.data.exercise.Exercise;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Routine_exercises {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    @JsonBackReference
    private Routine routine;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    @JsonBackReference
    private Exercise exercise;

    private int rep;
    private int set;
    private int day;

    public Routine_exercises() {}

    public Routine_exercises(int id, Routine routine, Exercise exercise, int rep, int set, int day) {
        this.id = id;
        this.routine = routine;
        this.exercise = exercise;
        this.rep = rep;
        this.set = set;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int amount) {
        this.rep = amount;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
