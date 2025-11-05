package com.example.elderexserver.data.webSocket;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionUpdateResult {
    private Integer exerciseId;
    private Integer currentCount;
}
