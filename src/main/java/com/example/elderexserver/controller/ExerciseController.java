package com.example.elderexserver.controller;

import com.example.elderexserver.Exception.ResourceNotFoundException;
import com.example.elderexserver.data.exercise.DTO.ExerciseListView;
import com.example.elderexserver.data.exercise.DTO.ExerciseView;
import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/list")
    public ResponseEntity<List<ExerciseListView>> getAllExercises() {
        List<ExerciseListView> exercises = exerciseService.getAllExercises();
        if (exercises.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseView> getExerciseById(@PathVariable int id) {
        // service throws ResourceNotFoundException if not found
        ExerciseView exercise = exerciseService.getExerciseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + id));
        return ResponseEntity.ok(exercise);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<Exercise> newExercise(@RequestBody Exercise exercise) {
        Exercise newExercise = exerciseService.newExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExercise);
    }
}
