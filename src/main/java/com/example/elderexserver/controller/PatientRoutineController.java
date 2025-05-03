package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ActualExerciseDetailListView;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.repository.PatientRoutineRepository;
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
    private PatientRoutineRepository patientRoutineRepository;

    @Autowired
    private PatientRoutineService patientRoutineService;

    @GetMapping("/dashboard/{caretakerId}")
    public List<PatientRoutineDashboardReportView> getPatientRoutineDashboardReport(@PathVariable Integer caretakerId) {
        return patientRoutineRepository.findPatientRoutineDashboardReport(caretakerId);
    }

    @GetMapping("/report/detail/{date}/{patientId}")
    public List<ActualExerciseDetailListView> getActualExerciseDetailListByPatientIdAndDate(@PathVariable String date, @PathVariable Integer patientId) {
        return patientRoutineRepository.findActualExerciseDetailListByPatientIdAndDate(date, patientId);
    }

    @GetMapping("/report/daily/{startDate}/{endDate}/{patientId}")
    public List<PatientDailyRoutineReport> getDailyRoutineReport(@PathVariable String startDate, @PathVariable  String endDate, @PathVariable  Integer patientId) {
        return patientRoutineService.getDailyRoutineReport(startDate, endDate, patientId);
    }

    @GetMapping("/report/week/{patientRoutineId}")
    public List<PatientWeeklyRoutineReport> getWeeklyRoutineReport(@PathVariable Integer patientRoutineId) {
        return patientRoutineService.getWeeklyRoutineReport(patientRoutineId);
    }

    @GetMapping("/report/{patientId}")
    public List<PatientRoutineView> getPatientRoutineByPatientId(@PathVariable Integer patientId) {
        return patientRoutineRepository.findPatientRoutineByPatientId(patientId);
    }

    @GetMapping("/chart/line/{patientId}")
    public List<PatientLineChart> getPatientRoutineChart(@PathVariable Integer patientId) {
        return patientRoutineService.getPatientLineChart(patientId);
    }
}
