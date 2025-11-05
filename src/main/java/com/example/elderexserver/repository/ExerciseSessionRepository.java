package com.example.elderexserver.repository;

import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistoryView;
import com.example.elderexserver.data.exercise.Exercise_Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseSessionRepository extends JpaRepository<Exercise_Session, Integer> {

    @Query(value = """
    SELECT
        es.id,
        es.patient_routine_id,
        CONCAT(
            FLOOR(
                TIMESTAMPDIFF(
                    SECOND,
                    es.start_time,
                    es.end_time
                ) / 60
            ),
            ' min ',
            MOD(
                TIMESTAMPDIFF(
                    SECOND,
                    es.start_time,
                    es.end_time
                ),
                60
            ),
            ' Sec'
        ) AS session_time,
        DATE(es.start_time) AS 'date',
        sd.exercise_id,
        e.name,
        SUM(sd.reps) AS reps
    FROM
        exercise_session es
    JOIN
        exercise_session_detail sd ON sd.session_id = es.id
    JOIN
        exercise e ON e.id = sd.exercise_id
    JOIN
        patient_routine pr ON pr.id = es.patient_routine_id
    WHERE
        pr.patient_id = :patientId
    GROUP BY
        es.id,
        es.patient_routine_id,
        es.start_time,
        sd.exercise_id
    ORDER BY
        es.start_time DESC;
    """, nativeQuery = true)
    List<ExerciseSessionHistoryView> findAllByPatientId(int patientId);
}
