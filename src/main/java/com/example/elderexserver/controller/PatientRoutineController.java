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
        int patientId = patientAuth.getPatientId();

        List<ExerciseSessionDetailListView> exerciseSessionDetailListViews = patientRoutineService.getActualExerciseDetailListByPatientIdAndDate(date, patientId);
        if (exerciseSessionDetailListViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(exerciseSessionDetailListViews);
    }

    @GetMapping("/report")
    public ResponseEntity<List<PatientRoutineView>> getPatientRoutineByPatientId(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatientId();

        List<PatientRoutineView> patientRoutineViews = patientRoutineService.getPatientRoutineByPatientId(patientId);
        if (patientRoutineViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(patientRoutineViews);
    }

    @GetMapping("/current")
    public ResponseEntity<PatientRoutineList> getCurrentPatientRoutine(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatientId();

        PatientRoutineList routine = patientRoutineService.getCurrentPatientRoutineByPatientId(patientId);
        if (routine == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(routine);
    }

    @GetMapping("/week")
    public ResponseEntity<PatientCurrentWeekProgressRoutine> getWeeklyRoutineProgress(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatientId();

        PatientCurrentWeekProgressRoutine weekProgress = patientRoutineService.getPatientCurrentWeekProgressRoutine(patientId);
        if (weekProgress == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(weekProgress);
    }

    @GetMapping("/day")
    public ResponseEntity<List<PatientCurrentDayProgressRoutineView>> getDayRoutineProgress(Authentication authentication) {
        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        int patientId = patientAuth.getPatientId();

        List<PatientCurrentDayProgressRoutineView> dailyProgress = patientRoutineService.getPatientCurrentDayRoutineByPatientId(patientId);
        if (dailyProgress.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dailyProgress);
    }
}
