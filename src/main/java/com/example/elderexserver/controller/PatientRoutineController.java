package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.service.PatientRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ExerciseSessionDetailListView> getActualExerciseDetailListByPatientIdAndDate(@PathVariable String date, @PathVariable Integer patientId) {
        return patientRoutineService.getActualExerciseDetailListByPatientIdAndDate(date, patientId);
    }

    @GetMapping("/report/{patientId}")
    public List<PatientRoutineView> getPatientRoutineByPatientId(@PathVariable Integer patientId) {
        return patientRoutineService.getPatientRoutineByPatientId(patientId);
    }
}
