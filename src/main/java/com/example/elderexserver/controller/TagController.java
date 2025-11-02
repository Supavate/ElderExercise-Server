package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.ExerciseTag;
import com.example.elderexserver.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private TagService tagService;

    @GetMapping("/list")
    public ResponseEntity<List<ExerciseTag>> getAllTags() {
        return ResponseEntity.ok(tagService.findAllTags());
    }

    @PostMapping("/new")
    public ResponseEntity<ExerciseTag> newExerciseTag(@RequestBody ExerciseTag tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.newExerciseTag(tag));
    }
}
