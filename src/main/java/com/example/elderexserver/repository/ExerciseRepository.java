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
            r.id AS routine_id,
            r.name AS routine_name,
            r.description AS routine_description,
            s.first_name AS staff_first_name,
            s.last_name AS staff_last_name,
            e.id AS exercise_id,
            e.name AS exercise_name,
            wd.id AS day_id,
            re.rep AS rep
        FROM routine r
        LEFT JOIN staff s ON r.staff_id = s.id
        LEFT JOIN routine_exercises re ON re.routine_id = r.id
        LEFT JOIN exercise e ON re.exercise_id = e.id
        LEFT JOIN week_day wd ON re.week_day_id = wd.id
    """, nativeQuery = true)
    List<RoutineListView> findRoutineList();

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
