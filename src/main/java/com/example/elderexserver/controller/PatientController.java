package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.repository.PatientRepository;
import com.example.elderexserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/new")
    public ResponseEntity<String> newPatient(@RequestBody NewPatient newPatient) {
        try {
            patientService.newPatient(newPatient);
            return ResponseEntity.ok("New patient added");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering new patient: " + e.getMessage());
        }
    }
}
