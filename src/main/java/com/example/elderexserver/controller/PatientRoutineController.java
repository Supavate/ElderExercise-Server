package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.service.PatientRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patient_routine")
public class PatientRoutineController {

    @Autowired
    private PatientRoutineService patientRoutineService;

    @GetMapping("/report/detail/{date}/{patientId}")
    public ResponseEntity<List<ExerciseSessionDetailListView>> getActualExerciseDetailListByPatientIdAndDate(@PathVariable String date, @PathVariable Integer patientId) {
        List<ExerciseSessionDetailListView> exerciseSessionDetailListViews = patientRoutineService.getActualExerciseDetailListByPatientIdAndDate(date, patientId);
        if (exerciseSessionDetailListViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(exerciseSessionDetailListViews);
    }

    @GetMapping("/report/{patientId}")
    public ResponseEntity<List<PatientRoutineView>> getPatientRoutineByPatientId(@PathVariable Integer patientId) {
        List<PatientRoutineView> patientRoutineViews = patientRoutineService.getPatientRoutineByPatientId(patientId);
        if (patientRoutineViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(patientRoutineViews);
    }
}
