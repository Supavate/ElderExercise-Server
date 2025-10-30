package com.example.elderexserver.data.routine.DTO;

public interface PatientProgressDashboardView {
    Integer getExerciseId();
    String getExerciseName();
    Integer getRepPerSet();
    Integer getSetPerDay();
    Integer getTargetReps();
    Integer getTotalRepsToday();
    Integer getWeeklyGoalHit();
    Integer getGoalDaysPerWeek();
}
