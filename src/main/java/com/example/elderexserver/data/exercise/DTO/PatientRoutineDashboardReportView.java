package com.example.elderexserver.data.exercise.DTO;

public interface PatientRoutineDashboardReportView {
    Integer getPatientId();
    String getFirstName();
    String getLastName();
    String getPeriodStart();
    String getPeriodEnd();
    Integer getTotalReps();
    Integer getRepGoal();
    Float getPercentageDone();
}
