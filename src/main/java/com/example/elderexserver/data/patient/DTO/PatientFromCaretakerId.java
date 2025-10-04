package com.example.elderexserver.data.patient.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatientFromCaretakerId {
    private Integer id;
    private String picture;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
}
