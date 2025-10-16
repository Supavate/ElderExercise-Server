package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.data.routine.Patient_Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRoutineRepository extends JpaRepository<Patient_Routine, Integer> {

    @Query(value = """
        SELECT
            e.name AS exercise_name,
            esd.*
        FROM
            exercise_session_detail esd
        JOIN exercise_session es ON
            esd.session_id = es.id
        JOIN patient_routine pr ON
            es.patient_routine_id = pr.id
        JOIN patient p ON
            pr.patient_id = p.id
        JOIN exercise e ON
            esd.exercise_id = e.id
        WHERE
            p.id =:patientId AND DATE(esd.start_time) =:date
        ORDER BY
            es.start_time,
            esd.start_time;
    """, nativeQuery = true)
    List<ExerciseSessionDetailListView> findActualExerciseDetailListByPatientIdAndDate(@Param("date") String date, @Param("patientId") Integer patientId);

    @Query(value = """
        SELECT
            r.name AS routine_name,
            r.description AS routine_description,
            pr.id AS patient_routine_id,
            pr.start_date,
            pr.end_date,
            e.id AS exercise_id,
            e.name AS exercise_name,
            re.rep,
            re.set,
            re.day
        FROM
            patient_routine pr
        LEFT JOIN ROUTINE r ON
            pr.routine_id = r.id
        LEFT JOIN routine_exercises re ON
            re.routine_id = pr.routine_id
        LEFT JOIN exercise e ON
            re.exercise_id = e.id
        WHERE
            pr.patient_id = :patientId
    """, nativeQuery = true)
    List<PatientRoutineView> findPatientRoutineByPatientId(@Param("patientId") Integer patientId);

    @Query(value = """
        SELECT
            r.name AS routine_name,
            r.description AS routine_description,
            pr.id AS patient_routine_id,
            pr.start_date,
            pr.end_date,
            e.id AS exercise_id,
            e.name AS exercise_name,
            re.rep,
            re.set,
            re.day
        FROM
            patient_routine pr
        LEFT JOIN ROUTINE r ON
            pr.routine_id = r.id
        LEFT JOIN routine_exercises re ON
            re.routine_id = pr.routine_id
        LEFT JOIN exercise e ON
            re.exercise_id = e.id
        WHERE
            pr.patient_id = :patientId AND pr.start_date < NOW() AND pr.end_date > NOW()
    """, nativeQuery = true)
    List<PatientRoutineView> findCurrentPatientRoutineByPatientId(@Param("patientId") Integer patientId);

    @Query(value = """
        SELECT
            pr.routine_id,
            r.name AS routine_name,
            e.id AS exercise_id,
            e.name AS exercise_name,
            COALESCE(
                COUNT(
                    DISTINCT CASE WHEN d.total_reps >(re.rep * re.set) THEN d.exercise_date
                END
            ),
            0
        ) AS goal_hit,
        re.day AS goal
        FROM
            patient_routine pr
        JOIN ROUTINE r ON
            r.id = pr.routine_id
        JOIN routine_exercises re ON
            re.routine_id = pr.routine_id
        JOIN exercise e ON
            e.id = re.exercise_id
        LEFT JOIN(
            SELECT es.patient_routine_id,
                sd.exercise_id,
                DATE(sd.start_time) AS exercise_date,
                SUM(sd.reps) AS total_reps
            FROM
                exercise_session_detail sd
            JOIN exercise_session es ON
                es.id = sd.session_id
            WHERE
                YEARWEEK(sd.start_time, 1) = YEARWEEK(NOW(), 1)
            GROUP BY
                es.patient_routine_id,
                sd.exercise_id,
                DATE(sd.start_time)) AS d
            ON
                d.exercise_id = e.id AND d.patient_routine_id = pr.id
            WHERE
                pr.patient_id = :patientId
            GROUP BY
                pr.routine_id,
                e.id,
                re.day;
        """, nativeQuery = true)
    List<PatientCurrentWeekProgressRoutineView> findCurrentWeekPatientRoutineStatusByPatientId(@Param("patientId") Integer patientId);

    @Query(value = """
        SELECT
            e.id AS exerciseId,
            e.name AS exerciseName,
            COALESCE(daily.total_reps, 0) AS totalReps,
            (re.rep * re.set) AS targetReps
        FROM
            patient_routine pr
        JOIN ROUTINE r ON
            r.id = pr.routine_id
        JOIN routine_exercises re ON
            re.routine_id = pr.routine_id
        JOIN exercise e ON
            e.id = re.exercise_id
        LEFT JOIN(
                es.patient_routine_id,
                sd.exercise_id,
                SUM(sd.reps) AS total_reps
            FROM
                exercise_session_detail sd
            JOIN exercise_session es ON
                es.id = sd.session_id
            WHERE
                DATE(sd.start_time) = DATE(NOW())
            GROUP BY
                es.patient_routine_id,
                sd.exercise_id) AS daily
            ON
                daily.exercise_id = e.id AND daily.patient_routine_id = pr.id
            WHERE
                pr.patient_id = :patientId
            ORDER BY
                pr.routine_id,
                e.id;
    """, nativeQuery = true)
    List<PatientCurrentDayProgressRoutineView> findCurrentDayPatientRoutineStatusByPatientId(@Param("patientId") Integer patientId);
}
