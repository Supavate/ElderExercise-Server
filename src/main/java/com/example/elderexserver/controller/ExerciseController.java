package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseListView;
import com.example.elderexserver.data.exercise.DTO.ExerciseView;
import com.example.elderexserver.data.exercise.DTO.RoutineList;
import com.example.elderexserver.repository.ExerciseRepository;
import com.example.elderexserver.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping("/list")
    public List<ExerciseListView> getAllExercises() {
        return exerciseRepository.findExerciseList();
    }

    @GetMapping("/{id}")
    public ExerciseView getExerciseById(@PathVariable int id) {
        return exerciseRepository.findExerciseById(id);
    }
}
