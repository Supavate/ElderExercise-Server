package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.service.PatientRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient_routine")
public class PatientRoutineController {

    @Autowired
    private PatientRoutineService patientRoutineService;

    @GetMapping("/report/detail/{date}")
    public ResponseEntity<List<ExerciseSessionDetailListView>> getActualExerciseDetailListByPatientIdAndDate(@PathVariable String date, Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatient().getId();

        List<ExerciseSessionDetailListView> exerciseSessionDetailListViews = patientRoutineService.getActualExerciseDetailListByPatientIdAndDate(date, patientId);
        if (exerciseSessionDetailListViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(exerciseSessionDetailListViews);
    }

    @GetMapping("/report")
    public ResponseEntity<List<PatientRoutineView>> getPatientRoutineByPatientId(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatient().getId();

        List<PatientRoutineView> patientRoutineViews = patientRoutineService.getPatientRoutineByPatientId(patientId);
        if (patientRoutineViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(patientRoutineViews);
    }

    @GetMapping("/current")
    public ResponseEntity<PatientRoutineList> getCurrentPatientRoutine(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatient().getId();

        PatientRoutineList routine = patientRoutineService.getCurrentPatientRoutineByPatientId(patientId);
        if (routine == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(routine);
    }

    @PostMapping("/new")
    public ResponseEntity<Patient_Routine> newPatientRoutine(@RequestBody NewPatientRoutine patientRoutine) {
        return ResponseEntity.ok(patientRoutineService.newPatientRoutine(patientRoutine));
    }
}
