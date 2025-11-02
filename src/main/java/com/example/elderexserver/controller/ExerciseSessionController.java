package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.NewExerciseSession;
import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistory;
import com.example.elderexserver.service.ExerciseSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class ExerciseSessionController {

    private final ExerciseSessionService exerciseSessionService;

    @GetMapping("/history")
    public ResponseEntity<List<ExerciseSessionHistory>> getAllHistoryByPatientId(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatientId();

        List<ExerciseSessionHistory> histories = exerciseSessionService.findAllHistoryByPatientId(patientId);
        if (histories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(histories);
    }

    @PostMapping("/new")
    public ResponseEntity<Exercise_Session> createNewSession(@RequestBody NewExerciseSession newExerciseSession) {
        Exercise_Session session = exerciseSessionService.newExerciseSession(newExerciseSession);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable int id) {
        exerciseSessionService.deleteExerciseSession(id);
        return ResponseEntity.ok().build();
    }
}

