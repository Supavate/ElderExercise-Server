package com.example.elderexserver.data.webSocket;

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
        return parseTime(startTime);
    }

    public LocalDateTime getEndTime() {
        return parseTime(endTime);
    }

    private LocalDateTime parseTime(String time) {
        if (time == null) return null;
        double timestamp = Double.parseDouble(time);
        long millis = (long) timestamp;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(millis),
                ZoneId.systemDefault()
        );
    }
}
