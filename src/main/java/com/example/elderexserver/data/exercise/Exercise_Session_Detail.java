package com.example.elderexserver.data.exercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exercise_Session_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @JsonBackReference
    private Exercise_Session exerciseSession;

    private Integer exercise_id;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private LocalDateTime start_time;
    private LocalDateTime end_time;

    private float avg_heart_rate;
    private int hr_zone1;
    private int hr_zone2;
    private int hr_zone3;
    private int hr_zone4;
    private int hr_zone5;
    private int reps;
    private float calories;

    @Lob
    @Column(name = "features")
    private String features;
}
