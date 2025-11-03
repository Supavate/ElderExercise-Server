package com.example.elderexserver.data.exercise.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDataEvent {
    private String sessionId;
    private String type;
    private FeaturesRequest data;
}
