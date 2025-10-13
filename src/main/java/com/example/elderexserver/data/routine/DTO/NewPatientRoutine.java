package com.example.elderexserver.data.routine.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class NewPatientRoutine {
    private Integer patientId;
    private Integer routineId;
    private String startDate;
    private String endDate;
}
