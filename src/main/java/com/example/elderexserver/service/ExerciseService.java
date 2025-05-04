package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseListView;
import com.example.elderexserver.data.exercise.DTO.ExerciseView;
import com.example.elderexserver.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<ExerciseListView> getAllExercises() {
        return exerciseRepository.findExerciseList();
    }

    public ExerciseView getExerciseById(int id) {
        return exerciseRepository.findExerciseById(id);
    }
}
