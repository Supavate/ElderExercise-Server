package com.example.elderexserver.controller;

import com.example.elderexserver.Exception.ResourceNotFoundException;
import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
@CrossOrigin(origins = "*")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/detail")
    public ResponseEntity<PatientDetail> getPatientDetailById(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatientId();

        PatientDetail patientDetail = patientService.getPatientDetailById(patientId);

        if (patientDetail == null) {
            throw new ResourceNotFoundException("Patient detail not found for ID: " + patientId);
        }

        return ResponseEntity.ok(patientDetail);
    }
}
