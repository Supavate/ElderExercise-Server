package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.repository.PatientRepository;
import com.example.elderexserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/age")
    public List<PatientWithAgeView> getPatientsWithAge() {
        return patientRepository.findAllWithAge();
    }

    @GetMapping("/allergy")
    public List<PatientWithAllergies> getAllergy() {
        return patientService.getPatientsWithAllergies();
    }

    @GetMapping("/staff/{id}")
    public List<PatientFromCaretakerIdView> getPatientFromCaretakerId(@PathVariable Integer id) {
        return patientRepository.findAllByCaretakerId(id);
    }

    @GetMapping("/detail/{id}")
    public PatientDetail getPatientDetailById(@PathVariable Integer id) {
        return patientService.getPatientDetailById(id);
    }

    @GetMapping("/list")
    public List<PatientListView> getPatientList() {
        return patientRepository.findPatientList();
    }
}
