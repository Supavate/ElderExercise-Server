package com.example.elderexserver.data.patient.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatientWithAge {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer weight;
    private Integer height;
    private Integer age;
}