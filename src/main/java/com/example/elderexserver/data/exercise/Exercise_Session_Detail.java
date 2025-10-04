package com.example.elderexserver.data.exercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Exercise_Session_Detail {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @JsonBackReference
    private Exercise_Session exerciseSession;

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

}
