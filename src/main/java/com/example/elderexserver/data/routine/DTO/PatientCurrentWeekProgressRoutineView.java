package com.example.elderexserver.data.routine.DTO;

public interface PatientCurrentWeekProgressRoutineView {
    int getRoutineId();
    String getRoutineName();
    int getExerciseId();
    String getExerciseName();
    int getGoalHit();
    int getGoal();
}
