package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseListView;
import com.example.elderexserver.data.exercise.DTO.ExerciseView;
import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/list")
    public ResponseEntity<List<ExerciseListView>> getAllExercises() {
        List<ExerciseListView> exercises = exerciseService.getAllExercises();
        if (exercises.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseView> getExerciseById(@PathVariable int id) {
        ExerciseView exercise = exerciseService.getExerciseById(id);
        if (exercise == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(exercise);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<Exercise> newExercise(@RequestBody Exercise exercise) {
        try {
            Exercise newExercise = exerciseService.newExercise(exercise);
            return ResponseEntity.ok(newExercise);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
