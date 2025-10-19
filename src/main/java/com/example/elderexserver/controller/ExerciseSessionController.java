package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistory;
import com.example.elderexserver.service.ExerciseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/session")
public class ExerciseSessionController {

    @Autowired
    private ExerciseSessionService exerciseSessionService;

    @GetMapping("/history")
    public ResponseEntity<List<ExerciseSessionHistory>> getAllHistoryByPatientId(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatientId();

        List<ExerciseSessionHistory> histories = exerciseSessionService.findAllHistoryByPatientId(patientId);
        if (histories.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }
}
