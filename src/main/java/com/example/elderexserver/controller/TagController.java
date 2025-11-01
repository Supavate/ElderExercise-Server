package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.ExerciseTag;
import com.example.elderexserver.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseEntity<List<ExerciseTag>> getAllTags() {
        try {
            return ResponseEntity.ok(tagService.findAllTags());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<ExerciseTag> newExerciseTag(@RequestBody ExerciseTag tag) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tagService.newExerciseTag(tag));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
