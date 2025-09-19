package com.example.elderexserver.data.routine.DTO;

public interface PatientRoutineView {
    String getRoutineName();
    String getRoutineDescription();
    Integer getPatientRoutineId();
    String getStartDate();
    String getEndDate();
    String getExerciseName();
    Integer getRep();
    Integer getSet();
    Integer getDay();
}
