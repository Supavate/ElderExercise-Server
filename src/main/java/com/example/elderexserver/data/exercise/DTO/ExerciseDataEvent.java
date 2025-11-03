package com.example.elderexserver.data.exercise.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDataEvent {
    private String sessionId;
    private String type;
    private Long startTime;
    private Long endTime;
    private FeaturesRequest data;

    public LocalDateTime getStartTime() {
        if (startTime == null) return null;
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault());
    }

    public LocalDateTime getEndTime() {
        if (endTime == null) return null;
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneId.systemDefault());
    }
}
