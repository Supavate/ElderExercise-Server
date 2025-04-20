package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.PatientWithAge;
import com.example.elderexserver.data.patient.DTO.PatientWithAllergies;
import com.example.elderexserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pat")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/age")
    public List<PatientWithAge> getPatientsWithAge() {
        return patientService.getPatientsWithAge();
    }

    @GetMapping("/allergy")
    public List<PatientWithAllergies> getAllergy() {
        return patientService.getPatientsWithAllergies();
    }
}
