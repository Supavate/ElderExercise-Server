package com.example.elderexserver.data.routine.DTO;

public interface PatientWeeklyRoutineReportView {
    Integer getPatientId();
    String getFirstName();
    String getLastName();
    Integer getYear();
    Integer getWeekNumber();
    String getWeekStartDate();
    String getWeekEndDate();
    Integer getExerciseId();
    String getExerciseName();
    Integer getTotalReps();
    Integer getWeeklyRepGoal();
    Float getPercentageDone();
}
