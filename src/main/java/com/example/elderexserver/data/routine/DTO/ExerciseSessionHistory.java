package com.example.elderexserver.data.routine.DTO;

import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExerciseSessionHistory {
    private String date;
    private Integer patientRoutineId;
    private List<Session> sessions;

    @Getter
    @AllArgsConstructor
    public static class Session {
        private Integer id;
        private String sessionTime;
        private List<Exercise> exercises;
    }

    @Getter
    @AllArgsConstructor
    public static class Exercise {
        private Integer exerciseId;
        private String exerciseName;
        private Integer reps;
    }
}
