package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.DTO.ActualExerciseDetailListView;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.data.routine.Patient_Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRoutineRepository extends JpaRepository<Patient_Routine, Integer> {

    @Query(value = """
        SELECT
            e.name AS exercise_name,
            aed.*
        FROM
            actual_exercise_detail aed
        JOIN actual_exercise ae ON
            aed.actual_exercise_id = ae.id
        JOIN patient_routine pr ON
            ae.patient_routine_id = pr.id
        JOIN patient p ON
            pr.patient_id = p.id
        JOIN exercise e ON
            aed.exercise_id = e.id
        WHERE
            p.id =:patientId AND DATE(aed.start_time) =:date
        ORDER BY
            ae.start_time,
            aed.start_time;
    """, nativeQuery = true)
    List<ActualExerciseDetailListView> findActualExerciseDetailListByPatientIdAndDate(String date, Integer patientId);

    @Query(value = """
        SELECT
            p.id AS patient_id,
            p.first_name,
            p.last_name,
            e.id AS exercise_id,
            e.name AS exercise,
            DATE(ae.start_time) AS exercise_date,
            SUM(aed.reps) AS total_reps,
            COALESCE(
                (
                SELECT
                    re2.rep
                FROM
                    routine_exercises re2
                WHERE
                    re2.routine_id = pr.routine_id AND re2.exercise_id = aed.exercise_id AND re2.week_day_id = DAYOFWEEK(ae.start_time) - 1
                LIMIT 1
            ),
            0
            ) AS rep_goal, IF(
                (
                SELECT
                    re2.rep
                FROM
                    routine_exercises re2
                WHERE
                    re2.routine_id = pr.routine_id AND re2.exercise_id = aed.exercise_id AND re2.week_day_id = DAYOFWEEK(ae.start_time) - 1
                LIMIT 1
            ) > 0,
            ROUND(
                (
                    SUM(aed.reps) /(
                    SELECT
                        re2.rep
                    FROM
                        routine_exercises re2
                    WHERE
                        re2.routine_id = pr.routine_id AND re2.exercise_id = aed.exercise_id AND re2.week_day_id = DAYOFWEEK(ae.start_time) - 1
                    LIMIT 1
                )
                ) * 100,
                1
            ),
            NULL
            ) AS percentage_done
        FROM
            patient p
        JOIN patient_routine pr ON
            p.id = pr.patient_id
        JOIN actual_exercise ae ON
            pr.id = ae.patient_routine_id
        JOIN actual_exercise_detail aed ON
            ae.id = aed.actual_exercise_id
        JOIN exercise e ON
            aed.exercise_id = e.id
        WHERE
            DATE(ae.start_time) >= :startDate AND DATE(ae.start_time) <= :endDate
            AND p.id =:patientId
        GROUP BY
            p.id,
            e.name,
            DATE(ae.start_time),
            pr.routine_id,
            aed.exercise_id
        ORDER BY
            p.last_name,
            p.first_name,
            exercise_date,
            e.name;
    """, nativeQuery = true)
    List<PatientDailyRoutineReportView> findPatientDailyRoutineReport(String startDate, String endDate, Integer patientId);

    @Query(value = """
        SELECT
            p.id AS patient_id,
            p.first_name,
            p.last_name,
            YEAR(ae.start_time) AS YEAR,
            WEEK(ae.start_time, 1) AS week_number,
            MIN(DATE(ae.start_time)) AS week_start_date,
            MAX(DATE(ae.start_time)) AS week_end_date,
            ae.id AS exercise_id,
            e.name AS exercise_name,
            SUM(aed.reps) AS total_reps,
            (
            SELECT
                SUM(re2.rep)
            FROM
                routine_exercises re2
            WHERE
                re2.routine_id = pr.routine_id AND re2.exercise_id = e.id AND re2.week_day_id BETWEEN 0 AND 6
        ) AS weekly_rep_goal,
        IF(
            (
            SELECT
                SUM(re2.rep)
            FROM
                routine_exercises re2
            WHERE
                re2.routine_id = pr.routine_id AND re2.exercise_id = e.id AND re2.week_day_id BETWEEN 0 AND 6
        ) > 0,
        ROUND(
            (
                SUM(aed.reps) /(
                SELECT
                    SUM(re2.rep)
                FROM
                    routine_exercises re2
                WHERE
                    re2.routine_id = pr.routine_id AND re2.exercise_id = e.id AND re2.week_day_id BETWEEN 0 AND 6
            )
            ) * 100,
            1
        ),
        NULL
        ) AS percentage_done
        FROM
            patient p
        JOIN patient_routine pr ON
            p.id = pr.patient_id
        JOIN actual_exercise ae ON
            pr.id = ae.patient_routine_id
        JOIN actual_exercise_detail aed ON
            ae.id = aed.actual_exercise_id
        JOIN exercise e ON
            aed.exercise_id = e.id
        WHERE
            pr.id =:patientRoutineId
        GROUP BY
            p.id,
            e.id,
            YEAR(ae.start_time),
            WEEK(ae.start_time, 1),
            pr.routine_id
        ORDER BY
            p.id,
            YEAR
        DESC
            ,
            week_number
        DESC
            ,
            e.name;
    """, nativeQuery = true)
    List<PatientWeeklyRoutineReportView> findPatientWeeklyRoutineReport(Integer patientRoutineId);

    @Query(value = """
    SELECT
        r.name AS routine_name,
        r.description AS routine_description,
        pr.id AS patient_routine_id,
        pr.start_date,
        pr.end_date
    FROM
        patient_routine pr
    JOIN
        routine r ON pr.routine_id = r.id
    WHERE
        pr.patient_id =:patient_id
    """, nativeQuery = true)
    List<PatientRoutineView> findPatientRoutineByPatientId(Integer patientId);

    @Query(value = """
        SELECT
            p.id AS patient_id,
            p.first_name,
            p.last_name,
            MIN(DATE(ae.start_time)) AS period_start,
            MAX(DATE(ae.start_time)) AS period_end,
            SUM(aed.reps) AS total_reps,
            (
            SELECT
                SUM(re2.rep)
            FROM
                routine_exercises re2
            WHERE
                re2.routine_id = pr.routine_id AND re2.week_day_id BETWEEN 0 AND 6
        ) AS rep_goal,
        IF(
            (
            SELECT
                SUM(re2.rep)
            FROM
                routine_exercises re2
            WHERE
                re2.routine_id = pr.routine_id AND re2.week_day_id BETWEEN 0 AND 6
        ) > 0,
        ROUND(
            (
                SUM(aed.reps) /(
                SELECT
                    SUM(re2.rep)
                FROM
                    routine_exercises re2
                WHERE
                    re2.routine_id = pr.routine_id AND re2.week_day_id BETWEEN 0 AND 6
            )
            ) * 100,
            1
        ),
        NULL
        ) AS percentage_done
        FROM
            patient p
        JOIN patient_routine pr ON
            p.id = pr.patient_id
        JOIN actual_exercise ae ON
            pr.id = ae.patient_routine_id
        JOIN actual_exercise_detail aed ON
            ae.id = aed.actual_exercise_id
        JOIN exercise e ON
            aed.exercise_id = e.id
        WHERE
            ae.start_time >= CURDATE() - INTERVAL 7 DAY
            AND p.caretaker_id =:caretaker_id
        GROUP BY
            p.id, pr.routine_id
        ORDER BY
            p.id
    """, nativeQuery = true)
    List<PatientRoutineDashboardReportView> findPatientRoutineDashboardReport(Integer caretakerId);
}
