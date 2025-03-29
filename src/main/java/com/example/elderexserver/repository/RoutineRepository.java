package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {
}
