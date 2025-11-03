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
    private String startTime;
    private String endTime;
    private FeaturesRequest data;

    public LocalDateTime getStartTime() {
        return parseStartTime(startTime);
    }

    public LocalDateTime getEndTime() {
        return parseStartTime(endTime);
    }

    private LocalDateTime parseStartTime(String time) {
        if (time == null) return null;
        double timestamp = Double.parseDouble(time);
        long millis = (long) timestamp;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(millis),
                ZoneId.systemDefault()
        );
    }
}
