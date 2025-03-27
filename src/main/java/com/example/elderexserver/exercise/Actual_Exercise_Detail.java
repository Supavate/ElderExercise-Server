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
    private float calorie;
}
