package com.example.elderexserver.data.exercise.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResultResponse {
    private String type;
    private String exerciseType;
    private Integer rep;
    private Long timestamp;
}
