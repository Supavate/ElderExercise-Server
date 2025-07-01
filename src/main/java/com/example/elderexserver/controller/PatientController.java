package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.*;
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

    @GetMapping("/age")
    public List<PatientWithAge> getPatientsWithAge() {
        return patientService.getPatientsWithAge();
    }

    @GetMapping("/staff/{id}")
    public List<PatientFromCaretakerIdView> getPatientFromCaretakerId(@PathVariable Integer id) {
        return patientService.getPatientFromCaretakerId(id);
    }

    @GetMapping("/detail/{id}")
    public PatientDetail getPatientDetailById(@PathVariable Integer id) {
        return patientService.getPatientDetailById(id);
    }

    @GetMapping("/list")
    public List<PatientListView> getPatientList() {
        return patientService.getPatientList();
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

    @PatchMapping("/update")
    public ResponseEntity<String> update(@RequestBody UpdatePatient updatePatient) {
        try {
            patientService.updatePatient(updatePatient);
            return ResponseEntity.ok("Patient updated");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating patient: " + e.getMessage());
        }
    }
}
