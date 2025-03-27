package com.example.elderexserver.exercise;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
public class Actual_Exercise {
    @Id
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date startTime;

    @Temporal(TemporalType.DATE)
    private Date endTime;

    @OneToMany(mappedBy = "actualExercise")
    @JsonManagedReference
    private List<Actual_Exercise_Detail> exercise_details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Actual_Exercise_Detail> getExercise_details() {
        return exercise_details;
    }

    public void setExercise_details(List<Actual_Exercise_Detail> exercise_details) {
        this.exercise_details = exercise_details;
    }
}