package com.example.elderexserver.exercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Routine_exercises {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    @JsonBackReference
    private Routine routine;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
    private int exercise_id;

    @ManyToOne
    @JoinColumn(name = "week_day_id")
    private Week_Day week_day;
    private int week_day_id;

    private int rep;

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

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public Week_Day getWeek_day() {
        return week_day;
    }

    public void setWeek_day(Week_Day week_day_id) {
        this.week_day = week_day_id;
    }

    public int getWeek_day_id() {
        return week_day_id;
    }

    public void setWeek_day_id(int week_day_id) {
        this.week_day_id = week_day_id;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int amount) {
        this.rep = amount;
    }
}
