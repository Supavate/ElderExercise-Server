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

    @Query(value = """
    WITH selected_sessions AS (
        SELECT
            id,
            patient_routine_id,
            start_time,
            end_time
        FROM exercise_session
        WHERE patient_routine_id IN (
            SELECT id
            FROM patient_routine\s
            WHERE patient_id = :patientId
        )
        AND end_time IS NOT NULL
        ORDER BY id DESC
        LIMIT :limitAmount OFFSET :offsetAmount
    )
    SELECT
        ss.id,
        ss.patient_routine_id,
        CONCAT(
            FLOOR(
                TIMESTAMPDIFF(
                    SECOND,
                    ss.start_time,
                    ss.end_time
                ) / 60
            ),
            ' min ',
            MOD(
                TIMESTAMPDIFF(
                    SECOND,
                    ss.start_time,
                    ss.end_time
                ),
                60
            ),
            ' Sec'
        ) AS session_time,
        DATE(ss.start_time) AS date,
        esd.exercise_id,
        e.name,
        SUM(esd.reps) AS reps
    FROM selected_sessions ss
    JOIN exercise_session_detail esd\s
        ON esd.session_id = ss.id
    JOIN exercise e\s
        ON e.id = esd.exercise_id
    JOIN patient_routine pr\s
        ON pr.id = ss.patient_routine_id
    GROUP BY
        ss.id,
        ss.patient_routine_id,
        ss.start_time,
        ss.end_time,
        esd.exercise_id,
        e.name
    ORDER BY
        ss.id DESC;
    """, nativeQuery = true)
    List<ExerciseSessionHistoryView> findAllByPatientId(int patientId, int limitAmount, int offsetAmount);
}
