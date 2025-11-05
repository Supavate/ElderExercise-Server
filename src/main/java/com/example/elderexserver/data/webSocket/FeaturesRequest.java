package com.example.elderexserver.data.webSocket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
public class FeaturesRequest {
    private List<Double> features;
    private float averageHearthRate;

    @JsonIgnore
    private final CompletableFuture<FeaturesResponse> responseFuture;
}
