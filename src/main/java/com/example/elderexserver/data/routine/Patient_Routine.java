package com.example.elderexserver.data.routine;

import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.patient.Patient;
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
}
