package com.example.elderexserver.data.exercise.DTO;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NewExerciseSession {
    private Integer patientRoutineId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Detail> details;

    @Getter
    public static class Detail {
        private Integer exerciseId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Integer reps;
    }
}
