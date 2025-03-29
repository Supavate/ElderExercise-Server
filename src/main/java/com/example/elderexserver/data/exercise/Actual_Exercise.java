package com.example.elderexserver.data.exercise;

import com.example.elderexserver.data.patient.Patient_Routine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Actual_Exercise {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_routine_id")
    @JsonBackReference
    private Patient_Routine patientRoutine;

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

    public Patient_Routine getPatientRoutine() {
        return patientRoutine;
    }

    public void setPatientRoutine(Patient_Routine patientRoutine) {
        this.patientRoutine = patientRoutine;
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