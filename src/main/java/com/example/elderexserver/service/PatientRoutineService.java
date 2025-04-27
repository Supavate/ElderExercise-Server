package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.PatientWeeklyRoutineReport;
import com.example.elderexserver.data.exercise.DTO.PatientWeeklyRoutineReportView;
import com.example.elderexserver.repository.PatientRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientRoutineService {

    @Autowired
    private PatientRoutineRepository patientRoutineRepository;

    public List<PatientWeeklyRoutineReport> getWeeklyRoutineReport() {
        List<PatientWeeklyRoutineReportView> patientWeeklyRoutineReport = patientRoutineRepository.findPatientWeeklyRoutineReport();

        Map<String, PatientWeeklyRoutineReport> reportMap = new LinkedHashMap<>();

        for (PatientWeeklyRoutineReportView row : patientWeeklyRoutineReport) {
            // Create a unique key for each report
            String mapKey = row.getPatientId() + "_" + row.getYear() + "_" + row.getWeekNumber();

            // Get or create report
            PatientWeeklyRoutineReport report = reportMap.computeIfAbsent(mapKey,
                    x -> new PatientWeeklyRoutineReport(mapKey, row.getPatientId(), row.getFirstName(), row.getLastName(), row.getYear(), row.getWeekNumber(), row.getWeekStartDate(), row.getWeekStartDate(), new HashSet<>()));

            // Create and add exercise to the report
            PatientWeeklyRoutineReport.Exercise exercise = new PatientWeeklyRoutineReport.Exercise(row.getExerciseId(), row.getExerciseName(), row.getTotalReps(), row.getWeeklyRepGoal(), row.getPercentageDone());
            report.getExerciseSet().add(exercise);
        }

        // Convert map values to list
        return new ArrayList<>(reportMap.values());
    }
}
