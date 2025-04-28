package com.example.elderexserver.data.routine.DTO;

import java.time.LocalDate;

public interface PatientDailyRoutineReportView {
    Integer getPatientId();
    String getFirstName();
    String getLastName();
    Integer getExerciseId();
    String getExerciseName();
    LocalDate getExerciseDate();
    Integer getTotalReps();
    Integer getRepGoal();
    Float getPercentageDone();
}
