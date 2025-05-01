package com.example.elderexserver.service;

import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.repository.PatientRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PatientRoutineService {

    @Autowired
    private PatientRoutineRepository patientRoutineRepository;

    public List<PatientWeeklyRoutineReport> getWeeklyRoutineReport(Integer patientRoutineId) {
        List<PatientWeeklyRoutineReportView> patientWeeklyRoutineReport = patientRoutineRepository.findPatientWeeklyRoutineReport(patientRoutineId);

        Map<String, PatientWeeklyRoutineReport> reportMap = new LinkedHashMap<>();

        for (PatientWeeklyRoutineReportView row : patientWeeklyRoutineReport) {
            // Create a unique key for each report
            String mapKey = row.getPatientId() + "_" + row.getYear() + "_" + row.getWeekNumber();

            // Get or create report
            PatientWeeklyRoutineReport report = reportMap.computeIfAbsent(mapKey,
                    x -> new PatientWeeklyRoutineReport(mapKey, row.getPatientId(), row.getFirstName(), row.getLastName(), row.getYear(), row.getWeekNumber(), row.getWeekStartDate(), row.getWeekEndDate(), new HashSet<>()));

            // Create and add exercise to the report
            PatientWeeklyRoutineReport.Exercise exercise = new PatientWeeklyRoutineReport.Exercise(row.getExerciseId(), row.getExerciseName(), row.getTotalReps(), row.getWeeklyRepGoal(), row.getPercentageDone());
            report.getExerciseSet().add(exercise);
        }

        // Convert map values to list
        return new ArrayList<>(reportMap.values());
    }

    public List<PatientDailyRoutineReport> getDailyRoutineReport(String startDate, String endDate, Integer patientId) {
        List<PatientDailyRoutineReportView> patientDailyRoutineReport = patientRoutineRepository.findPatientDailyRoutineReport(startDate, endDate, patientId);

        Map<LocalDate, PatientDailyRoutineReport> reportMap = new LinkedHashMap<>();

        for (PatientDailyRoutineReportView row : patientDailyRoutineReport) {
            PatientDailyRoutineReport report = reportMap.computeIfAbsent(row.getExerciseDate(),
                    x -> new PatientDailyRoutineReport(row.getPatientId(), row.getFirstName(), row.getLastName(), row.getExerciseDate(), new HashSet<>()));

            PatientDailyRoutineReport.Exercise exercise = new PatientDailyRoutineReport.Exercise(row.getExerciseId(), row.getExerciseName(), row.getTotalReps(), row.getRepGoal(), row.getPercentageDone());
            report.getExerciseSet().add(exercise);
        }

        return new ArrayList<>(reportMap.values());
    }

    public List<PatientLineChart> getPatientLineChart(Integer patientId) {
        List<PatientLineChartView> patientLineChart = patientRoutineRepository.findPatientLineChartView(patientId);

        Map<String, Map<Integer, PatientLineChart.Exercise>> weeklyExerciseMap = new LinkedHashMap<>();

        for (PatientLineChartView row : patientLineChart) {
            String weekKey = row.getYear() + "_" + row.getWeekNumber();
            PatientLineChart.Day day = new PatientLineChart.Day(row.getTotalReps(), row.getRepGoal());

            weeklyExerciseMap
                    .computeIfAbsent(weekKey, k -> new HashMap<>())
                    .computeIfAbsent(row.getExerciseId(), id -> new PatientLineChart.Exercise(
                            row.getExerciseId(),
                            row.getExerciseName(),
                            new LinkedHashSet<>()
                    ))
                    .getDaySet()
                    .add(day);
        }

        List<PatientLineChart> patientLineChartList = new ArrayList<>();
        for (Map<Integer, PatientLineChart.Exercise> exerciseMap : weeklyExerciseMap.values()) {
            patientLineChartList.add(new PatientLineChart(new LinkedHashSet<>(exerciseMap.values())));
        }

        return patientLineChartList;
    }
}
