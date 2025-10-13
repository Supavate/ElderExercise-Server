package com.example.elderexserver.data.routine;

import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.patient.Patient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
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
    private LocalDate start_date;

    @Temporal(TemporalType.DATE)
    private LocalDate end_date;

    @OneToMany(mappedBy = "patientRoutine")
    @JsonManagedReference
    private List<Exercise_Session> exercises;

    public Patient_Routine(Patient patient, Routine routine, LocalDate start_date, LocalDate end_date) {
        this.patient = patient;
        this.routine = routine;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
