package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.FeaturesRequest;
import com.example.elderexserver.data.exercise.DTO.FeaturesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassificationService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public FeaturesResponse classify(FeaturesRequest request) {
        log.info("Classification request received");
        log.info(request.getFeatures().toString());
        CompletableFuture<FeaturesResponse> responseFuture = new CompletableFuture<>();

        FeaturesRequest eventRequest = new FeaturesRequest(
                request.getFeatures(),
                request.getAverageHearthRate(),
                responseFuture);
        applicationEventPublisher.publishEvent(eventRequest);

        try {
            FeaturesResponse response = responseFuture.get(15, TimeUnit.SECONDS);
            log.info("Response received : {}", response.getExercise_name());
            return response;
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout waiting for classification response", e);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error getting classification response", e);
        }
    }
}
