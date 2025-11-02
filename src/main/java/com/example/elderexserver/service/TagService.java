package com.example.elderexserver.service;

import com.example.elderexserver.Exception.ResourceNotFoundException;
import com.example.elderexserver.data.exercise.ExerciseTag;
import com.example.elderexserver.repository.ExerciseTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final ExerciseTagRepository exerciseTagRepository;

    public List<ExerciseTag> findAllTags() {
        List<ExerciseTag> tags = exerciseTagRepository.findAll();
        if (tags.isEmpty()) throw new ResourceNotFoundException("No Tags found");
        return tags;
    }

    public ExerciseTag newExerciseTag(ExerciseTag tag) {
        return exerciseTagRepository.save(tag);
    }
}
