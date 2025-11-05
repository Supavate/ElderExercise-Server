package com.example.elderexserver.service;

import com.example.elderexserver.data.webSocket.FeaturesRequest;
import com.example.elderexserver.data.webSocket.FeaturesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class ExternalServerService {

    private final WebClient webClient;

    @Value("${external.server.url}")
    private String externalServerUrl;

    public ExternalServerService(WebClient.Builder webClient) {
        this.webClient = WebClient.builder().build();
    }

    @EventListener
    @Async
    public void handleClassifyEvent(FeaturesRequest request) {
        try {
            FeaturesResponse response = webClient.post()
                    .uri(externalServerUrl + "/predict")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(FeaturesResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();

            request.getResponseFuture().complete(response);
        } catch (Exception e) {
            request.getResponseFuture().completeExceptionally(
                    new RuntimeException("Failed to get exercise: " + e.getMessage(), e)
            );
        }
    }
}
