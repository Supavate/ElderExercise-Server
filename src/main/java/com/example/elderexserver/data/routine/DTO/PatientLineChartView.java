package com.example.elderexserver.data.routine.DTO;

public interface PatientLineChartView {
    Integer getExerciseId();
    String getExerciseName();
    Integer getYear();
    Integer getWeekNumber();
    Integer getDayOfWeek();
    String getDayName();
    Integer getTotalReps();
    Integer getRepGoal();
}
