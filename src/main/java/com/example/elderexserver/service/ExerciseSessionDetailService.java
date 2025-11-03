package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseDataEvent;
import com.example.elderexserver.data.exercise.DTO.FeaturesResponse;
import com.example.elderexserver.data.exercise.Exercise_Session_Detail;
import com.example.elderexserver.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExerciseSessionDetailService {
    private final ExerciseRepository exerciseRepository;

    public Exercise_Session_Detail createSessionDetail(FeaturesResponse response, ExerciseDataEvent event) {
        Exercise_Session_Detail detail = new Exercise_Session_Detail();
        detail.setExercise(exerciseRepository.findById(response.getExercise_id()).orElse(null));
        detail.setReps(1);
        detail.setStart_time(event.getStartTime());
        detail.setEnd_time(event.getEndTime());
        return detail;
    }
}
