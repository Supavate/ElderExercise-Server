package com.example.elderexserver.data.exercise;

import com.example.elderexserver.data.routine.Patient_Routine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Exercise_Session {
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

    @OneToMany(mappedBy = "exerciseSession")
    @JsonManagedReference
    private List<Exercise_Session_Detail> exercise_session_details;

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

    public List<Exercise_Session_Detail> getExercise_session_details() {
        return exercise_session_details;
    }

    public void setExercise_session_details(List<Exercise_Session_Detail> exercise_details) {
        this.exercise_session_details = exercise_details;
    }
}