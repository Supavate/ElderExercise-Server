package com.example.elderexserver.exercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Actual_Exercise_Detail {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "actual_exercise_id")
    @JsonBackReference
    private Actual_Exercise actualExercise;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Temporal(TemporalType.DATE)
    private Date start_time;

    @Temporal(TemporalType.DATE)
    private Date end_time;

    private float avg_heart_rate;
    private int hr_zone1;
    private int hr_zone2;
    private int hr_zone3;
    private int hr_zone4;
    private int hr_zone5;
    private int reps;
    private float calories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Actual_Exercise getActualExercise() {
        return actualExercise;
    }

    public void setActualExercise(Actual_Exercise actualExercise) {
        this.actualExercise = actualExercise;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public float getAvg_heart_rate() {
        return avg_heart_rate;
    }

    public void setAvg_heart_rate(float avg_heart_rate) {
        this.avg_heart_rate = avg_heart_rate;
    }

    public int getHr_zone1() {
        return hr_zone1;
    }

    public void setHr_zone1(int hr_zone1) {
        this.hr_zone1 = hr_zone1;
    }

    public int getHr_zone2() {
        return hr_zone2;
    }

    public void setHr_zone2(int hr_zone2) {
        this.hr_zone2 = hr_zone2;
    }

    public int getHr_zone3() {
        return hr_zone3;
    }

    public void setHr_zone3(int hr_zone3) {
        this.hr_zone3 = hr_zone3;
    }

    public int getHr_zone4() {
        return hr_zone4;
    }

    public void setHr_zone4(int hr_zone4) {
        this.hr_zone4 = hr_zone4;
    }

    public int getHr_zone5() {
        return hr_zone5;
    }

    public void setHr_zone5(int hr_zone5) {
        this.hr_zone5 = hr_zone5;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calorie) {
        this.calories = calorie;
    }
}
