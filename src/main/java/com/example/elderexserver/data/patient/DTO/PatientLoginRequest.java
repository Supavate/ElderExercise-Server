package com.example.elderexserver.data.patient.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientLoginRequest {
    private String email;
    private String password;
}