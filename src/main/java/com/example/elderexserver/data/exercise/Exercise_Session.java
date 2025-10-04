package com.example.elderexserver.data.exercise;

import com.example.elderexserver.data.routine.Patient_Routine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
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

}