package com.example.elderexserver;

import jakarta.persistence.*;

@Entity
public class Routines_exercises {
    @EmbeddedId
    RoutinesExercisesKey id;

    @ManyToOne
    @MapsId("routine_id")
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @ManyToOne
    @MapsId("exercise_id")
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private int amount;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
