package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.routine.DTO.NewPatientRoutine;
import com.example.elderexserver.data.routine.DTO.PatientRoutineList;
import com.example.elderexserver.data.routine.DTO.PatientRoutineView;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.service.PatientRoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient_routine")
@PreAuthorize("hasRole('STAFF')")
@RequiredArgsConstructor
public class PatientRoutineControllerForStaff {

    private final PatientRoutineService patientRoutineService;

    @PostMapping("/new")
    public ResponseEntity<Patient_Routine> newPatientRoutine(@RequestBody NewPatientRoutine patientRoutine) {
        Patient_Routine createdRoutine = patientRoutineService.newPatientRoutine(patientRoutine);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoutine);
    }
}
