package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.DTO.ExerciseListView;
import com.example.elderexserver.data.exercise.DTO.ExerciseView;
import com.example.elderexserver.data.exercise.DTO.RoutineListView;
import com.example.elderexserver.data.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    @Query(value = """
        SELECT
            e.id,
            t.icon,
            e.name,
            e.description
        FROM exercise e
        LEFT JOIN exercise_tag t ON e.tag_id = t.id;
    """, nativeQuery = true)
    List<ExerciseListView> findExerciseList();

    @Query(value = """
        SELECT
            e.id,
            e.video_url,
            e.name,
            e.description,
            t.name as tag,
            t.icon
        FROM exercise e
        LEFT JOIN exercise_tag t ON e.tag_id = t.id
        WHERE e.id=:id;
    """, nativeQuery = true)
    ExerciseView findExerciseById(int id);
}
