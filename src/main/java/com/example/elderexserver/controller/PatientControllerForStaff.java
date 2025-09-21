package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasRole('STAFF')")
@CrossOrigin(origins = "*")
public class PatientControllerForStaff {
    @Autowired
    private PatientService patientService;

    @GetMapping("/age")
    public ResponseEntity<List<PatientWithAge>> getPatientsWithAge() {
        List<PatientWithAge> patients = patientService.getPatientsWithAge();
        if (patients.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<List<PatientFromCaretakerIdView>> getPatientFromCaretakerId(@PathVariable Integer id) {
        List<PatientFromCaretakerIdView> patients = patientService.getPatientFromCaretakerId(id);
        if (patients.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PatientDetail> getPatientDetailById(@PathVariable Integer id) {
        PatientDetail patientDetail = patientService.getPatientDetailById(id);
        if (patientDetail == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(patientDetail);
    }

    @GetMapping("/list")
    public List<PatientListView> getPatientList() {
        return patientService.getPatientList();
    }

    @PostMapping("/new")
    public ResponseEntity<?> newPatient(@RequestBody NewPatient newPatient) {
        try {
            Patient patient = patientService.newPatient(newPatient);
            PatientDetail patientDetail = patientService.getPatientDetailById(patient.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(patientDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering new patient: " + e.getMessage());
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updatePatient(@RequestBody UpdatePatient updatePatient) {
        try {
            Patient patient = patientService.updatePatient(updatePatient);
            PatientDetail patientDetail = patientService.getPatientDetailById(patient.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating patient: " + e.getMessage());
        }
    }
}
