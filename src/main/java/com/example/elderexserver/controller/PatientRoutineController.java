package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.PatientRoutineDashboardReport;
import com.example.elderexserver.data.exercise.DTO.PatientRoutineDashboardReportView;
import com.example.elderexserver.data.exercise.DTO.PatientWeeklyRoutineReport;
import com.example.elderexserver.repository.PatientRoutineRepository;
import com.example.elderexserver.service.PatientRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/report/week")
    public List<PatientWeeklyRoutineReport> getWeeklyRoutineReport() {
        return patientRoutineService.getWeeklyRoutineReport();
    }

    @GetMapping("/dashboard")
    public List<PatientRoutineDashboardReportView> getPatientRoutineDashboardReport() {
        return patientRoutineRepository.findPatientRoutineDashboardReport();
    }
}
