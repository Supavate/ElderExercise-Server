package com.example.elderexserver.data.routine.DTO;

import java.time.LocalDate;

public interface PatientWeeklyRoutineReportView {
    Integer getPatientId();
    String getFirstName();
    String getLastName();
    Integer getYear();
    Integer getWeekNumber();
    LocalDate getWeekStartDate();
    LocalDate getWeekEndDate();
    Integer getExerciseId();
    String getExerciseName();
    Integer getTotalReps();
    Integer getWeeklyRepGoal();
    Float getPercentageDone();
}
