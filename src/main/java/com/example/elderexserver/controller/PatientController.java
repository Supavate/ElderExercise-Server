package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "*")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/detail")
    public ResponseEntity<PatientDetail> getPatientDetailById(Authentication authentication) {
        try {
            PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
            int patientId = patientAuth.getPatientId();

            PatientDetail patientDetail = patientService.getPatientDetailById(patientId);
            if (patientDetail == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(patientDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
