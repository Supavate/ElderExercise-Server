package com.example.elderexserver.data.routine.DTO;

public interface PatientBarChartView {
    String getRoutineName();
    String getRoutineDescription();
    Integer getExerciseId();
    String getExerciseName();
    Integer getYear();
    Integer getWeekNumber();
    Integer getTotalDone();
    Integer getRepGoal();
    Integer getMissingReps();
}
