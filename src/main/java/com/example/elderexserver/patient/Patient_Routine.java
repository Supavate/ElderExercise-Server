package com.example.elderexserver.patient;

import com.example.elderexserver.exercise.Actual_Exercise;
import com.example.elderexserver.exercise.Routine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Patient_Routine {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    @JsonManagedReference
    private Routine routine;

    @Temporal(TemporalType.DATE)
    private Date start_date;
    private int week;

    @OneToMany(mappedBy = "patientRoutine")
    @JsonManagedReference
    private List<Actual_Exercise> exercises;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public List<Actual_Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Actual_Exercise> exercises) {
        this.exercises = exercises;
    }
}
