package com.example.elderexserver.data.webSocket;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionUpdateResult {
    private Integer exerciseId;
    private String exerciseName;
    private Integer currentCount;
}
