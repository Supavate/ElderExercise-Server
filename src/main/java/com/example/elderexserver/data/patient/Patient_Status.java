package com.example.elderexserver.data.patient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Patient_Status {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Temporal(value = TemporalType.DATE)
    private LocalDate date;

    public Patient_Status(Status status, LocalDate date) {
        this.status = status;
        this.date = date;
    }
}
