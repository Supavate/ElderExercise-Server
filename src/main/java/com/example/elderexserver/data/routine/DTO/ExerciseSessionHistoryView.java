package com.example.elderexserver.data.routine.DTO;

public interface ExerciseSessionHistoryView {
    Integer getId();
    Integer getPatientRoutineId();
    String getSessionTime();
    String getDate();
    Integer getExerciseId();
    String getName();
    Integer getReps();
}
