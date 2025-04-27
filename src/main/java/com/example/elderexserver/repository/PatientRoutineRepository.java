package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.DTO.*;
import com.example.elderexserver.data.patient.Patient_Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRoutineRepository extends JpaRepository<Patient_Routine, Integer> {

    @Query(value = """
        SELECT
            p.id AS patient_id,
            p.first_name,
            p.last_name,
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
    List<PatientDailyExerciseReport> findPatientDailyExerciseReport();

    @Query(value = """
        SELECT
            p.id AS patient_id,
            p.first_name,
            p.last_name,
            DATE(ae.start_time) AS exercise_date,
            SUM(aed.reps) AS total_reps,
            (
            SELECT
                SUM(re2.rep)
            FROM
                routine_exercises re2
            WHERE
                re2.routine_id = pr.routine_id AND re2.week_day_id = DAYOFWEEK(DATE(ae.start_time)) - 1
        ) AS rep_goal,
        IF(
            (
            SELECT
                SUM(re2.rep)
            FROM
                routine_exercises re2
            WHERE
                re2.routine_id = pr.routine_id AND re2.week_day_id = DAYOFWEEK(DATE(ae.start_time)) - 1
        ) > 0,
        ROUND(
            (
                SUM(aed.reps) /(
                SELECT
                    SUM(re2.rep)
                FROM
                    routine_exercises re2
                WHERE
                    re2.routine_id = pr.routine_id AND re2.week_day_id = DAYOFWEEK(DATE(ae.start_time)) - 1
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
        GROUP BY
            p.id,
            DATE(ae.start_time),
            pr.routine_id
        ORDER BY
            p.last_name,
            p.first_name,
            exercise_date;
    """, nativeQuery = true)
    List<PatientDailyRoutineReport> findPatientDailyRoutineReport();

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
    List<PatientWeeklyRoutineReportView> findPatientWeeklyRoutineReport();

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
        GROUP BY
            p.id, pr.routine_id
        ORDER BY
            p.id
    """, nativeQuery = true)
    List<PatientRoutineDashboardReportView> findPatientRoutineDashboardReport();
}
