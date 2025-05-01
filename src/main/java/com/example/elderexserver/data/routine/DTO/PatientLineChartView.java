package com.example.elderexserver.data.routine.DTO;

public interface PatientLineChartView {
    Integer getExerciseId();
    String getExerciseName();
    Integer getYear();
    Integer getWeekNumber();
    Integer getDayOfWeek();
    Integer getTotalReps();
    Integer getRepGoal();
}
