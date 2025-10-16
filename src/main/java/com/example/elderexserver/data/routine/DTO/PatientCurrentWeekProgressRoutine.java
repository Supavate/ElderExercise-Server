package com.example.elderexserver.data.routine.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatientCurrentWeekProgressRoutine {
    private Integer routineId;
    private String routineName;
    private List<Exercise> exercises;

    @Getter
    @AllArgsConstructor
    public static class Exercise{
        private Integer exerciseId;
        private String exerciseName;
        private Integer goalHit;
        private Integer goal;
    }
}
