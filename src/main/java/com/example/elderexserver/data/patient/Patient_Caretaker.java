package com.example.elderexserver.data.patient;

import com.example.elderexserver.data.staff.Staff;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Patient_Caretaker {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "caretaker_id")
    private Staff caretaker;

    @Temporal(TemporalType.DATE)
    private Date date;
}
