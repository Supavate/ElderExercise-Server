package com.example.elderexserver.data.exercise.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResultResponse {
    private String type;
    private Long timestamp;
    private List<SessionExercis> exercises;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SessionExercis {
        private String exerciseType;
        private Integer rep;
    }
}
