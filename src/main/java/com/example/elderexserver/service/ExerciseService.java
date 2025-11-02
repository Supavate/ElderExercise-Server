package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseListView;
import com.example.elderexserver.data.exercise.DTO.ExerciseView;
import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<ExerciseListView> getAllExercises() {
        return exerciseRepository.findExerciseList();
    }

    public Optional<ExerciseView> getExerciseById(int id) {
        return Optional.ofNullable(exerciseRepository.findExerciseById(id));
    }

    public Exercise newExercise(Exercise newExercise) {
        return exerciseRepository.save(newExercise);
    }
}

