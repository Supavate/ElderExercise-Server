package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.ExerciseTag;
import com.example.elderexserver.repository.ExerciseTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private ExerciseTagRepository exerciseTagRepository;

    public List<ExerciseTag> findAllTags() {
        return exerciseTagRepository.findAll();
    }

    public ExerciseTag newExerciseTag(ExerciseTag tag) {
        return exerciseTagRepository.save(tag);
    }
}
