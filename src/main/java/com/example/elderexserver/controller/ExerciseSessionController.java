package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.NewExerciseSession;
import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistory;
import com.example.elderexserver.service.ExerciseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/new")
    public ResponseEntity<Exercise_Session> createNewSession(@RequestBody NewExerciseSession newExerciseSession) {
        try {
            Exercise_Session session = exerciseSessionService.newExerciseSession(newExerciseSession);
            return new ResponseEntity<>(session, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create exercise session: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable int id) {
        try {
            exerciseSessionService.deleteExerciseSession(id);
            return ResponseEntity.ok("Exercise session deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Exercise session not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete exercise session");
        }
    }
}
