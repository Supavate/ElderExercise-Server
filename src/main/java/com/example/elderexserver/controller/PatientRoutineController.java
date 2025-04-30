package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ActualExerciseDetailListView;
import com.example.elderexserver.data.routine.DTO.PatientDailyRoutineReport;
import com.example.elderexserver.data.routine.DTO.PatientRoutineDashboardReportView;
import com.example.elderexserver.data.routine.DTO.PatientWeeklyRoutineReport;
import com.example.elderexserver.repository.PatientRoutineRepository;
import com.example.elderexserver.service.PatientRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pr")
public class PatientRoutineController {
    @Autowired
    private PatientRoutineRepository patientRoutineRepository;

    @Autowired
    private PatientRoutineService patientRoutineService;

    @GetMapping("/dashboard")
    public List<PatientRoutineDashboardReportView> getPatientRoutineDashboardReport() {
        return patientRoutineRepository.findPatientRoutineDashboardReport();
    }

    @GetMapping("/aed/{patientId}/{date}")
    public List<ActualExerciseDetailListView> getActualExerciseDetailListByPatientIdAndDate(@PathVariable Integer patientId, @PathVariable String date) {
        return patientRoutineRepository.findActualExerciseDetailListByPatientIdAndDate(patientId, date);
    }

    @GetMapping("/report/daily")
    public List<PatientDailyRoutineReport> getDailyRoutineReport() {
        return patientRoutineService.getDailyRoutineReport();
    }

    @GetMapping("/report/week")
    public List<PatientWeeklyRoutineReport> getWeeklyRoutineReport() {
        return patientRoutineService.getWeeklyRoutineReport();
    }

}
