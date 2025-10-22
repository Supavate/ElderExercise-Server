package com.example.elderexserver.data.exercise;

import com.example.elderexserver.data.routine.Patient_Routine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Exercise_Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_routine_id")
    @JsonBackReference
    private Patient_Routine patientRoutine;

    private LocalDateTime start_time;
    private LocalDateTime end_time;

    @OneToMany(mappedBy = "exerciseSession", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Exercise_Session_Detail> exercise_session_details;
}