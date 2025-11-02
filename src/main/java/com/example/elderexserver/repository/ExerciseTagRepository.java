package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.ExerciseTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseTagRepository extends JpaRepository<ExerciseTag, Integer> {
}
