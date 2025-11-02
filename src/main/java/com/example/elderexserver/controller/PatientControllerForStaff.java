package com.example.elderexserver.controller;

import com.example.elderexserver.Exception.ResourceNotFoundException;
import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.service.PatientService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PatientControllerForStaff {

    private final PatientService patientService;

    @GetMapping("/age")
    public ResponseEntity<List<PatientWithAge>> getPatientsWithAge() {
        List<PatientWithAge> patients = patientService.getPatientsWithAge();
        if (patients.isEmpty()) {
            throw new ResourceNotFoundException("No patients with age found");
        }
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<List<PatientFromCaretakerIdView>> getPatientFromCaretakerId(@PathVariable Integer id) {
        List<PatientFromCaretakerIdView> patients = patientService.getPatientFromCaretakerId(id);
        if (patients.isEmpty()) {
            throw new ResourceNotFoundException("No patients found for caretaker ID: " + id);
        }
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PatientDetail> getPatientDetailById(@PathVariable Integer id) {
        PatientDetail patientDetail = patientService.getPatientDetailById(id);
        if (patientDetail == null) {
            throw new ResourceNotFoundException("Patient detail not found for ID: " + id);
        }
        return ResponseEntity.ok(patientDetail);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientListView>> getPatientList() {
        List<PatientListView> patients = patientService.getPatientList();
        if (patients.isEmpty()) {
            throw new ResourceNotFoundException("No patients found");
        }
        return ResponseEntity.ok(patients);
    }

    @PostMapping("/new")
    public ResponseEntity<PatientDetail> newPatient(@RequestBody NewPatient newPatient) {
        Patient patient = patientService.newPatient(newPatient);
        PatientDetail patientDetail = patientService.getPatientDetailById(patient.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(patientDetail);
    }

    @PatchMapping("/update")
    public ResponseEntity<PatientDetail> updatePatient(@RequestBody UpdatePatient updatePatient) {
        Patient patient = patientService.updatePatient(updatePatient);
        PatientDetail patientDetail = patientService.getPatientDetailById(patient.getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientDetail);
    }
}
