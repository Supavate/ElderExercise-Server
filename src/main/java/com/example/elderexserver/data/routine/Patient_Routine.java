package com.example.elderexserver.data.routine;

import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.patient.Patient;
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
    private Date end_date;

    @OneToMany(mappedBy = "patientRoutine")
    @JsonManagedReference
    private List<Exercise_Session> exercises;

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

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public List<Exercise_Session> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise_Session> exercises) {
        this.exercises = exercises;
    }
}
