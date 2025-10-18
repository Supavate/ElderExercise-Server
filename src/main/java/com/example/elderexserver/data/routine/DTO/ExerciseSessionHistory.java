package com.example.elderexserver.data.routine.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExerciseSessionHistory {
    private Integer id;
    private Integer patientRoutineId;
    private String sessionTime;
    private String date;
    private List<Exercise> exercises;

    @Getter
    @AllArgsConstructor
    public static class Exercise {
        private Integer exerciseId;
        private String exerciseName;
        private Integer reps;
    }
}
