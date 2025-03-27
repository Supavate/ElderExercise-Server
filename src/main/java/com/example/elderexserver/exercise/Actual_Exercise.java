package com.example.elderexserver.exercise;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Actual_Exercise {
    @Id
    private Integer id;
    private int patient_routine_id;

    @Temporal(TemporalType.DATE)
    private Date start_time;

    @Temporal(TemporalType.DATE)
    private Date end_time;

    @OneToMany(mappedBy = "actualExercise")
    @JsonManagedReference
    private List<Actual_Exercise_Detail> exercise_details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPatient_routine_id() {
        return patient_routine_id;
    }

    public void setPatient_routine_id(int patient_routine_id) {
        this.patient_routine_id = patient_routine_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date startTime) {
        this.start_time = startTime;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date endTime) {
        this.end_time = endTime;
    }

    public List<Actual_Exercise_Detail> getExercise_details() {
        return exercise_details;
    }

    public void setExercise_details(List<Actual_Exercise_Detail> exercise_details) {
        this.exercise_details = exercise_details;
    }
}