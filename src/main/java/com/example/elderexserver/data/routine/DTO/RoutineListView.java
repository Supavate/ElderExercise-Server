package com.example.elderexserver.data.routine.DTO;

public interface RoutineListView {
    Integer getRoutineId();
    String getRoutineName();
    String getRoutineDescription();
    String getStaffFirstName();
    String getStaffLastName();
    Integer getExerciseId();
    String getExerciseName();
    Integer getRep();
    Integer getSet();
    Integer getDay();
}