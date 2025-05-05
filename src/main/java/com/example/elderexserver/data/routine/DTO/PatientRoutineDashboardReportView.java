package com.example.elderexserver.data.routine.DTO;

public interface PatientRoutineDashboardReportView {
    Integer getPatientId();
    String getFirstName();
    String getLastName();
    Integer getAge();
    Integer getGender();
    String getPeriodStart();
    String getPeriodEnd();
    Integer getTotalReps();
    Integer getRepGoal();
    Float getPercentageDone();
}
