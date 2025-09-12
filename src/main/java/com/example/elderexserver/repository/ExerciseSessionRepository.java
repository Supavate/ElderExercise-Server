package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.Exercise_Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseSessionRepository extends JpaRepository<Exercise_Session, Integer> {
}
