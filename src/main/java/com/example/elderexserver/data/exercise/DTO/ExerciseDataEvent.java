package com.example.elderexserver.data.exercise.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDataEvent {
    private String sessionId;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private FeaturesRequest data;
}
