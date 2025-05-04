package com.example.elderexserver.repository;

import com.example.elderexserver.data.routine.DTO.RoutineListView;
import com.example.elderexserver.data.routine.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {
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
        WHERE r.id = :routineId
    """, nativeQuery = true)
    List<RoutineListView> findRoutineListById(Integer routineId);
}
