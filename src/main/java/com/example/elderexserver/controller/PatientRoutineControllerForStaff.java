package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.routine.DTO.PatientRoutineList;
import com.example.elderexserver.data.routine.DTO.PatientRoutineView;
import com.example.elderexserver.service.PatientRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patient_routine")
@PreAuthorize("hasRole('STAFF')")
public class PatientRoutineControllerForStaff {

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

    @GetMapping("/current/{patientId}")
    public ResponseEntity<PatientRoutineList> getCurrentPatientRoutine(@PathVariable Integer patientId) {
        PatientRoutineList patientRoutineListView = patientRoutineService.getCurrentPatientRoutineByPatientId(patientId);
        if (patientRoutineListView == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(patientRoutineListView);
    }
}
