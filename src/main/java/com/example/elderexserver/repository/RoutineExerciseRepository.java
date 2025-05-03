package com.example.elderexserver.repository;

import com.example.elderexserver.data.routine.Routine_exercises;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineExerciseRepository extends JpaRepository<Routine_exercises, Integer> {
}
