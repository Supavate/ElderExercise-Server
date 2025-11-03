package com.example.elderexserver.data.exercise.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
public class FeaturesRequest {
    private List<Double> features;
    private float averageHearthRate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @JsonIgnore
    private final CompletableFuture<FeaturesResponse> responseFuture;
}
