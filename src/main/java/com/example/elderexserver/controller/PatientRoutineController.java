package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.service.PatientRoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient_routine")
public class PatientRoutineController {

    private final PatientRoutineService patientRoutineService;

    @GetMapping("/report/detail/{date}")
    public ResponseEntity<List<ExerciseSessionDetailListView>> getActualExerciseDetailListByPatientIdAndDate(
            @PathVariable String date, Authentication authentication) {

        int patientId = ((PatientAuth) authentication.getPrincipal()).getPatientId();
        List<ExerciseSessionDetailListView> list = patientRoutineService.getActualExerciseDetailListByPatientIdAndDate(date, patientId);

        return list.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/report")
    public ResponseEntity<List<PatientRoutineView>> getPatientRoutineByPatientId(Authentication authentication) {
        int patientId = ((PatientAuth) authentication.getPrincipal()).getPatientId();
        List<PatientRoutineView> routines = patientRoutineService.getPatientRoutineByPatientId(patientId);
        return routines.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(routines);
    }

    @GetMapping("/current")
    public ResponseEntity<PatientRoutineList> getCurrentPatientRoutine(Authentication authentication) {
        int patientId = ((PatientAuth) authentication.getPrincipal()).getPatientId();
        PatientRoutineList routine = patientRoutineService.getCurrentPatientRoutineByPatientId(patientId);
        return routine == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(routine);
    }

    @GetMapping("/week")
    public ResponseEntity<PatientCurrentWeekProgressRoutine> getWeeklyRoutineProgress(Authentication authentication) {
        int patientId = ((PatientAuth) authentication.getPrincipal()).getPatientId();
        PatientCurrentWeekProgressRoutine weekProgress = patientRoutineService.getPatientCurrentWeekProgressRoutine(patientId);
        return weekProgress == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(weekProgress);
    }

    @GetMapping("/day")
    public ResponseEntity<List<PatientCurrentDayProgressRoutineView>> getDayRoutineProgress(Authentication authentication) {
        int patientId = ((PatientAuth) authentication.getPrincipal()).getPatientId();
        List<PatientCurrentDayProgressRoutineView> dailyProgress = patientRoutineService.getPatientCurrentDayRoutineByPatientId(patientId);
        return dailyProgress.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dailyProgress);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<PatientProgressDashboardView>> getDashboardRoutineProgress(Authentication authentication) {
        int patientId = ((PatientAuth) authentication.getPrincipal()).getPatientId();
        List<PatientProgressDashboardView> dashboard = patientRoutineService.getPatientCurrentWeekRoutineByPatientId(patientId);
        return dashboard.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dashboard);
    }
}
